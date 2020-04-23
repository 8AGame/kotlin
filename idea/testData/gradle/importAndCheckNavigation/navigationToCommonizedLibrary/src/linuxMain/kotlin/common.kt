package common

import kotlinx.cinterop.CPointer
import platform.posix.FILE
import platform.posix.fopen
import platform.zlib.uInt

fun test() {
    val file: CPointer< /* NAVIGATION-TARGET:expect class FILE */ FILE> = fopen("file.txt", "r") ?: return
    fun f1(): /* NAVIGATION-TARGET:expect class uInt */ uInt = TODO()
}

