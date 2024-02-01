package eu.steffo.twom.avatar.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.InputStream

fun InputStream.readRawBitmap(): Bitmap {
    return BitmapFactory.decodeStream(this)
}
