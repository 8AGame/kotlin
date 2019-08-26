// IGNORE_BACKEND: WASM
// IGNORE_BACKEND: JS_IR
// TODO: muted automatically, investigate should it be ran for JS or not
// DONT_RUN_GENERATED_CODE: JS
// IGNORE_BACKEND: JS

<!NO_TAIL_CALLS_FOUND!>tailrec fun test(counter : Int) : Int<!> {
    if (counter == 0) return 0

    try {
        throw Exception()
    } catch (e : Exception) {
        return <!TAIL_RECURSION_IN_TRY_IS_NOT_SUPPORTED!>test<!>(counter - 1)
    }
}

fun box() : String = if (test(3) == 0) "OK" else "FAIL"
// DONT_TARGET_EXACT_BACKEND: WASM
 //DONT_TARGET_WASM_REASON: IGNORED_IN_JS
