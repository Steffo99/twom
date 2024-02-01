package eu.steffo.twom.avatar.utils

import android.content.ContentResolver
import android.graphics.Bitmap
import android.net.Uri

fun getSqOrBitmapFromUri(contentResolver: ContentResolver, uri: Uri): Bitmap? {
    contentResolver.openInputStream(uri).use { is1 ->
        is1 ?: return null
        contentResolver.openInputStream(uri).use { is2 ->
            is2 ?: return null
            return getSqOrBitmapFromStreams(
                inputStream1 = is1,
                inputStream2 = is2,
            )
        }
    }
}
