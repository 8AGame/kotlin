// IGNORE_BACKEND: WASM
// IGNORE_BACKEND_FIR: JVM_IR
// IGNORE_BACKEND: JS_IR
// IGNORE_BACKEND: JS, NATIVE
// WITH_RUNTIME

fun box(): String {
    val s = StringBuilder()

    val xs = StringBuilder("abcd")

    for ((index, x) in xs.withIndex()) {
        s.append("$index:$x;")
        xs.setLength(0)
    }

    val ss = s.toString()
    return if (ss == "0:a;") "OK" else "fail: '$ss'"
}
// DONT_TARGET_EXACT_BACKEND: WASM
 //DONT_TARGET_WASM_REASON: UNRESOLVED_REF__ StringBuilder 
