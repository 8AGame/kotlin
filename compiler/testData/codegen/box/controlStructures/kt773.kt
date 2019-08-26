// IGNORE_BACKEND: WASM
package demo2

fun print(o : Any?) {}

fun test(i : Int) {
    var monthString : String? = "<empty>"
        when (i) {
        1 -> {
            print(1)
            print(2)
            print(3)
            print(4)
            print(5)
        }
       else -> {
            monthString = "Invalid month"
        }
    }
    print(monthString)
}

fun box() : String {
    for (i in 1..12) test(i)
    return "OK"
}

// DONT_TARGET_EXACT_BACKEND: WASM
 //DONT_TARGET_WASM_REASON: UNRESOLVED_REF__ .. 
