// IGNORE_BACKEND: WASM
// IGNORE_BACKEND_FIR: JVM_IR
// KJS_WITH_FULL_RUNTIME
// WITH_RUNTIME

fun box(): String {
    val res = (1..3).map { it ->
        if (it == 1)
            2
    };

    var result = ""
    for (i in res)
        result += " "
    return if (result == "   ") "OK" else result
}

// DONT_TARGET_EXACT_BACKEND: WASM
 //DONT_TARGET_WASM_REASON: UNRESOLVED_REF__ .. 
