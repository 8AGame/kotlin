// IGNORE_BACKEND: WASM
// IGNORE_BACKEND: JS_IR
// TODO: muted automatically, investigate should it be ran for JS or not
// IGNORE_BACKEND: JS, NATIVE

class A() {
    fun foo() {
        System.out?.println(1)
    }
}

fun box() : String {
    val a : A = A()
    return "OK"
}

// DONT_TARGET_EXACT_BACKEND: WASM
 //DONT_TARGET_WASM_REASON: UNRESOLVED_REF__ System 
