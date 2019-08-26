// !LANGUAGE: +InlineClasses
// IGNORE_BACKEND: WASM
// IGNORE_BACKEND_FIR: JVM_IR
// WITH_RUNTIME
// KJS_WITH_FULL_RUNTIME

class C<T>(val x: T, vararg ys: UInt) {
    val y0 = ys[0]
}

fun box(): String {
    val c = C("a", 42u)
    if (c.y0 != 42u) throw AssertionError()

    return "OK"
}
// DONT_TARGET_EXACT_BACKEND: WASM
 //DONT_TARGET_WASM_REASON: UNRESOLVED_REF__ UInt 
