// !LANGUAGE: +ProperIeee754Comparisons
// IGNORE_BACKEND: WASM
// It doesn't work on JS due to how numbers are represented, but it could be changed in the future.
// IGNORE_BACKEND: JS
// IGNORE_BACKEND: JS_IR

fun eq_double_any(a: Double, b: Any) = a == b

fun eq_double_anyN(a: Double, b: Any?) = a == b

fun eq_doubleN_any(a: Double?, b: Any) = a == b

fun eq_doubleN_anyN(a: Double?, b: Any?) = a == b

fun box(): String {
    if (eq_double_any(0.0, 0)) throw AssertionError("eq_double_any(0.0, 0)")
    if (eq_double_anyN(0.0, 0)) throw AssertionError("eq_double_anyN(0.0, 0)")
    if (eq_doubleN_any(0.0, 0)) throw AssertionError("eq_doubleN_any(0.0, 0)")
    if (eq_doubleN_anyN(0.0, 0)) throw AssertionError("eq_doubleN_anyN(0.0, 0)")

    return "OK"
}
// DONT_TARGET_EXACT_BACKEND: WASM
 //DONT_TARGET_WASM_REASON: IGNORED_IN_JS
