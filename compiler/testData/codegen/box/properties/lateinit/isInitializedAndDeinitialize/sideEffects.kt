// IGNORE_BACKEND: WASM
// IGNORE_BACKEND_FIR: JVM_IR
// WITH_RUNTIME

class Foo {
    lateinit var bar: String

    fun test(): String {
        var state = 0
        if (run { state++; this }::bar.isInitialized) return "Fail 1"

        bar = "A"
        if (!run { state++; this }::bar.isInitialized) return "Fail 3"

        return if (state == 2) "OK" else "Fail: state=$state"
    }
}

fun box(): String {
    return Foo().test()
}

// DONT_TARGET_EXACT_BACKEND: WASM
 //DONT_TARGET_WASM_REASON: UNRESOLVED_REF__ run 
