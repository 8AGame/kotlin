// IGNORE_BACKEND: WASM
// WITH_RUNTIME

var xs = intArrayOf(1, 2, 3)

fun box(): String {
    var sum = 0
    for (x in xs) {
        sum = sum * 10 + x
        xs = IntArray(0)
    }
    return if (sum == 123) "OK" else "Fail: $sum"
}
// DONT_TARGET_EXACT_BACKEND: WASM
 //DONT_TARGET_WASM_REASON: WASM_ARRAYS_UNSUPPORTED
