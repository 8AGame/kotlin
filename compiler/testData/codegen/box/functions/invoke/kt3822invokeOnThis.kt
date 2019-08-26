// IGNORE_BACKEND: WASM
// IGNORE_BACKEND: JS_IR
//KT-3822 Compiler crashes when use invoke convention with `this` in class which extends Function0<T>
// IGNORE_BACKEND: JS
// JS backend does not allow to implement Function{N} interfaces

class B() : Function0<Boolean> {
    override fun invoke() = true

    fun foo() = this() // Exception
}

fun box() = if (B().foo()) "OK" else "fail"
// DONT_TARGET_EXACT_BACKEND: WASM
 //DONT_TARGET_WASM_REASON: IGNORED_IN_JS
