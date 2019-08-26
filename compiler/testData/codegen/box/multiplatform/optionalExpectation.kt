// !LANGUAGE: +MultiPlatformProjects
// !USE_EXPERIMENTAL: kotlin.ExperimentalMultiplatform
// IGNORE_BACKEND: WASM
// IGNORE_BACKEND: NATIVE
// WITH_RUNTIME
// MODULE: library
// FILE: expected.kt

package a

@OptionalExpectation
expect annotation class A(val x: Int)

@OptionalExpectation
expect annotation class B(val s: String)

// FILE: actual.kt

package a

actual annotation class A(actual val x: Int)

// MODULE: main(library)
// FILE: main.kt

@file:Suppress("OPTIONAL_DECLARATION_USAGE_IN_NON_COMMON_SOURCE") // TODO: support common sources in the test infrastructure

package usage

import a.A
import a.B

@A(42)
@B("OK")
fun box(): String {
    return "OK"
}

// DONT_TARGET_EXACT_BACKEND: WASM
 //DONT_TARGET_WASM_REASON: UNRESOLVED_REF__ OptionalExpectation 
