// IGNORE_BACKEND: WASM
fun box() : String {
    val a = IntArray (5)
    var i = 0
    var sum = 0
    for(el in 0..4) {
       a[i] = i++
    }
    for (el in a) {
        sum = sum + el
    }
    if(sum != 10) return "a failed"

    return "OK"
}

// DONT_TARGET_EXACT_BACKEND: WASM
 //DONT_TARGET_WASM_REASON: UNRESOLVED_REF__ .. 
