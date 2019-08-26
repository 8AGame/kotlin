// !LANGUAGE: +InlineClasses
// IGNORE_BACKEND: WASM
// IGNORE_BACKEND_FIR: JVM_IR

class Foo {
    var a: Int = 42
    var d by DelegateFactory(0)
}

var provideDelegateInvoked = 0
var setterInvoked = 0

inline class DelegateFactory(val default: Int) {
    operator fun provideDelegate(thisRef: Any?, prop: Any?): Delegate {
        provideDelegateInvoked++
        return Delegate(default)
    }
}

inline class Delegate(val default: Int) {

    operator fun getValue(thisRef: Any?, prop: Any?) =
        (thisRef as? Foo)?.a ?: default

    operator fun setValue(thisRef: Any?, prop: Any?, newValue: Int) {
        setterInvoked++
        if (thisRef is Foo) {
            thisRef.a = newValue
        }
    }
}


fun box(): String {
    val x = Foo()
    if (x.d != 42) throw AssertionError()

    x.d = 1234
    if (x.d != 1234) throw AssertionError()
    if (x.a != 1234) throw AssertionError()

    if (setterInvoked != 1) throw AssertionError()
    if (provideDelegateInvoked != 1) throw AssertionError()

    return "OK"
}
// DONT_TARGET_EXACT_BACKEND: WASM
 //DONT_TARGET_WASM_REASON: PROPERTY_REFERENCE
