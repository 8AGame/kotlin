// IGNORE_BACKEND: WASM
// IGNORE_BACKEND_FIR: JVM_IR
// KJS_WITH_FULL_RUNTIME
// WITH_RUNTIME

abstract class BaseClass {
    protected open val menuId: Int = 0

    public fun run(): Pair<String, Boolean> =
            "$menuId" to (menuId == 0)
}

class ImplClass: BaseClass() {
    override val menuId: Int = 3
}

public fun box(): String {
    val result = ImplClass().run()

    if (result != ("3" to false)) return "Fail: $result"

    return "OK"
}

// DONT_TARGET_EXACT_BACKEND: WASM
 //DONT_TARGET_WASM_REASON: UNRESOLVED_REF__ Pair 
