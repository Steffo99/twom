package eu.steffo.twom.avatar.utils

import android.graphics.Bitmap
import java.io.File

fun getSqOrBitmapFromUri(file: File): Bitmap {
    file.inputStream().use { is1 ->
        file.inputStream().use { is2 ->
            return getSqOrBitmapFromStreams(
                inputStream1 = is1,
                inputStream2 = is2,
            )
        }
    }
}
