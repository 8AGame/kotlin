// !LANGUAGE: +InlineClasses
// IGNORE_BACKEND: WASM
// IGNORE_BACKEND_FIR: JVM_IR

class Foo {
    companion object {
        var a: Int = 42
        private var d by Delegate(0)

        fun d() = d
        fun d(newValue: Int) { d = newValue }
    }
}

var setterInvoked = 0

inline class Delegate(val ignored: Int) {

    operator fun getValue(thisRef: Any?, prop: Any?) = Foo.a

    operator fun setValue(thisRef: Any?, prop: Any?, newValue: Int) {
        setterInvoked++
        Foo.a = newValue
    }
}


fun box(): String {
    if (Foo.d() != 42) throw AssertionError()

    Foo.d(1234)
    if (Foo.d() != 1234) throw AssertionError()
    if (Foo.a != 1234) throw AssertionError()

    if (setterInvoked != 1) throw AssertionError()

    return "OK"
}
// DONT_TARGET_EXACT_BACKEND: WASM
 //DONT_TARGET_WASM_REASON: PROPERTY_REFERENCE
