// !LANGUAGE: +InlineClasses
// IGNORE_BACKEND: WASM
// IGNORE_BACKEND_FIR: JVM_IR

inline class Foo<T>(val x: Any) {
    fun bar() {}
}

fun <T, K> transform(f: Foo<T>): Foo<K> {
    return when {
        true -> f as Foo<K>
        else -> TODO()
    }
}

fun box(): String {
    val f = Foo<Int>(42)
    val t = transform<Int, Number>(f)
    return if (t.x !is Number) "Fail" else "OK"
}

// DONT_TARGET_EXACT_BACKEND: WASM
 //DONT_TARGET_WASM_REASON: UNRESOLVED_REF__ TODO 
