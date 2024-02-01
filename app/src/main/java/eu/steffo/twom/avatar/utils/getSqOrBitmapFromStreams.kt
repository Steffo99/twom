package eu.steffo.twom.avatar.utils

import android.graphics.Bitmap
import readExifOrientation
import java.io.InputStream

fun getSqOrBitmapFromStreams(inputStream1: InputStream, inputStream2: InputStream): Bitmap {
    var orientation: Int
    inputStream1.use {
        orientation = it.readExifOrientation()
    }

    lateinit var bitmap: Bitmap
    inputStream2.use {
        bitmap = it.readRawBitmap()
    }

    return bitmap.squareAndOrient(orientation)
}
