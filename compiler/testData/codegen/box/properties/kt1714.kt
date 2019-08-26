// IGNORE_BACKEND: WASM
// IGNORE_BACKEND_FIR: JVM_IR
// IGNORE_BACKEND: JS_IR
// TODO: muted automatically, investigate should it be ran for JS or not
// IGNORE_BACKEND: JS, NATIVE

interface A {
    val method : (() -> Unit )?
    val test : Integer
}

class AImpl : A {
    override val method : (() -> Unit )? = {
    }
    override val test : Integer = Integer(777)
}

fun test(a : A) {
    val method = a.method
    if (method != null) {
        method()
    }
}

public fun box() : String {
    AImpl().test
    test(AImpl())
    return "OK"
}

// DONT_TARGET_EXACT_BACKEND: WASM
 //DONT_TARGET_WASM_REASON: UNRESOLVED_REF__ Integer 
