// IGNORE_BACKEND: WASM
class A {
    private lateinit var str: String

    public fun getMyStr(): String {
        try {
            val a = str
        } catch (e: RuntimeException) {
            return "OK"
        }

        return "FAIL"
    }
}

fun box(): String {
    val a = A()
    return a.getMyStr()
}
// DONT_TARGET_EXACT_BACKEND: WASM
 //DONT_TARGET_WASM_REASON: UNRESOLVED_REF__ RuntimeException 
