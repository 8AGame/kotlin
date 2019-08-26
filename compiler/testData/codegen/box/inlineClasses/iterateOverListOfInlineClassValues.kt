// IGNORE_BACKEND: WASM
// IGNORE_BACKEND_FIR: JVM_IR
// KJS_WITH_FULL_RUNTIME
// !LANGUAGE: +InlineClasses
// WITH_RUNTIME

inline class Foo(val arg: String)

fun box(): String {
    val ls = listOf(Foo("abc"), Foo("def"))
    var res = ""
    for (el in ls) {
        res += el.arg
    }

    return if (res != "abcdef") "Fail" else "OK"
}

// DONT_TARGET_EXACT_BACKEND: WASM
 //DONT_TARGET_WASM_REASON: UNRESOLVED_REF__ listOf 
