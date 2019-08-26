// IGNORE_BACKEND: WASM
// IGNORE_BACKEND_FIR: JVM_IR

sealed class Result {
    class Failure(val exception: Exception) : Result()
    class Success(val message: String) : Result()
}

fun box(): String {
    var result: Result
    try {
        result = Result.Success("OK")
    }
    catch (e: Exception) {
        result = Result.Failure(Exception())
    }

    when (result) {
        is Result.Failure -> throw result.exception
        is Result.Success -> return result.message
    }
}
// DONT_TARGET_EXACT_BACKEND: WASM
 //DONT_TARGET_WASM_REASON: UNRESOLVED_REF__ Exception 
