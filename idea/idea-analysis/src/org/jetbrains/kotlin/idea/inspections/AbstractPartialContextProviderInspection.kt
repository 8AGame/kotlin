/*
 * Copyright 2010-2020 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.idea.inspections

import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.LocalInspectionToolSession
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.openapi.util.Key
import com.intellij.openapi.util.KeyWithDefaultValue
import com.intellij.psi.PsiElementVisitor
import org.jetbrains.kotlin.caches.resolve.KotlinCacheService
import org.jetbrains.kotlin.idea.caches.resolve.analyze
import org.jetbrains.kotlin.idea.util.logger
import org.jetbrains.kotlin.psi.KtElement
import org.jetbrains.kotlin.psi.KtExpression
import org.jetbrains.kotlin.resolve.BindingContext
import org.jetbrains.kotlin.resolve.calls.callUtil.getResolvedCall
import org.jetbrains.kotlin.resolve.lazy.BodyResolveMode
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong

abstract class AbstractPartialContextProviderInspection : AbstractKotlinInspection() {
    val log by logger

    override fun inspectionStarted(session: LocalInspectionToolSession, isOnTheFly: Boolean) {
        super.inspectionStarted(session, isOnTheFly)
        session.getUserData(KEY)?.inspectionStarted(this)
    }

    override fun inspectionFinished(session: LocalInspectionToolSession, problemsHolder: ProblemsHolder) {
        super.inspectionFinished(session, problemsHolder)
        val bindingContextProvider = session.getUserData(KEY)
        bindingContextProvider?.inspectionFinished(this)
    }

    final override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor {
        throw IllegalStateException("buildVisitor(holder,isOnTheFly,bindingContextProvider) should be used instead.")
    }

    final override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean, session: LocalInspectionToolSession): PsiElementVisitor {
        val bindingContextProvider = session.putUserDataIfAbsent(KEY, PartialBindingContextProvider(session))
        return buildVisitor(holder, isOnTheFly, bindingContextProvider)
    }

    abstract fun buildVisitor(
        holder: ProblemsHolder,
        isOnTheFly: Boolean,
        bindingContextProvider: PartialBindingContextProvider
    ): PsiElementVisitor

    companion object {
        private val KEY = Key<PartialBindingContextProvider>("LazySessionPartialResolverBindingContextProvider")
    }
}

class PartialBindingContextProvider(val session: LocalInspectionToolSession) {
    val log by logger
    private val kotlinCacheService: KotlinCacheService by lazy { KotlinCacheService.getInstance(session.file.project) }
    private val sessionStoreResolve: ConcurrentHashMap<KtElementResolveMode, BindingContext> by lazy {
        log.debug("PartialBindingContxtProvider created.")
        ConcurrentHashMap<KtElementResolveMode, BindingContext>(
            session.endOffset - session.startOffset * 2
        )
    }
    private val counter = AtomicLong()
    private val missCounter = AtomicLong()
    private val partialToCfaUpgradeCounter = AtomicLong()

    fun resolve(element: KtElement, resolveMode: BodyResolveMode = BodyResolveMode.PARTIAL): BindingContext? {
        counter.incrementAndGet()
        val cacheKey = KtElementResolveMode(element, resolveMode)
        return sessionStoreResolve.computeIfAbsent(cacheKey) {
            if (cacheKey.bodyResolveMode == BodyResolveMode.PARTIAL) {
                // try to lookup PARTIAL_WITH_CFA
                val cfaResolved = sessionStoreResolve.get(KtElementResolveMode(element, BodyResolveMode.PARTIAL_WITH_CFA))
                if (cfaResolved != null) {
                    partialToCfaUpgradeCounter.incrementAndGet()
                    return@computeIfAbsent cfaResolved
                }
            }
            missCounter.incrementAndGet()
            val facade = kotlinCacheService.getResolutionFacade(listOf(element))
            element.analyze(facade, resolveMode)
        }
    }

    fun resolveToCall(element: KtElement) =
        resolve(element)?.let { element.getResolvedCall(it) }

    fun getCallableDescriptor(element: KtExpression) =
        resolveToCall(element)?.resultingDescriptor

    fun inspectionFinished(inspectionTool: LocalInspectionTool) {
        log.debug("Inspection session finished: ${session.to} ${counter}/${missCounter}/${partialToCfaUpgradeCounter}")
    }

    fun inspectionStarted(inspectionTool: LocalInspectionTool) {
        log.debug("Inspection started on session ${session} ${session.file} ${session.startOffset} ${session.endOffset}")
    }
}

data class KtElementResolveMode(val element: KtElement, val bodyResolveMode: BodyResolveMode)