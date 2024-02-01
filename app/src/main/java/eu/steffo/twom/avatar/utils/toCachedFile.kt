package eu.steffo.twom.avatar.utils

import android.graphics.Bitmap
import java.io.File

fun Bitmap.toCachedFile(id: String): File {
    val file = File.createTempFile("bitmap_$id", ".jpg")
    file.outputStream().use {
        this.compress(Bitmap.CompressFormat.JPEG, 85, it)
        it.flush()
    }
    return file
}
