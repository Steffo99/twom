package eu.steffo.twom.create

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import androidx.exifinterface.media.ExifInterface
import java.io.File

class ImageHandler {
    companion object {
        fun getOrientation(contentResolver: ContentResolver, uri: Uri): Int? {
            contentResolver.openInputStream(uri).use {
                if (it == null) {
                    return null
                } else {
                    return ExifInterface(it).getAttributeInt(ExifInterface.TAG_ORIENTATION, 1)
                }
            }
        }

        fun getRawBitmap(contentResolver: ContentResolver, uri: Uri): Bitmap? {
            contentResolver.openInputStream(uri).use {
                if (it == null) {
                    return null
                } else {
                    return BitmapFactory.decodeStream(it)
                }
            }
        }

        fun squareAndOrient(
            bitmap: Bitmap,
            orientation: Int = ExifInterface.ORIENTATION_NORMAL
        ): Bitmap {
            // Determine the starting points and the size to crop the image to a 1:1 square
            val xStart: Int
            val yStart: Int
            val size: Int
            if (bitmap.width > bitmap.height) {
                yStart = 0
                xStart = (bitmap.width - bitmap.height) / 2
                size = bitmap.height
            } else {
                xStart = 0
                yStart = (bitmap.height - bitmap.width) / 2
                size = bitmap.width
            }

            // Create a transformation matrix to rotate the bitmap based on the orientation
            val transformationMatrix = Matrix()

            // TODO: Make sure all these transformations are valid
            when (orientation) {
                ExifInterface.ORIENTATION_FLIP_HORIZONTAL -> {
                    transformationMatrix.postScale(-1f, 1f)
                }

                ExifInterface.ORIENTATION_ROTATE_180 -> {
                    transformationMatrix.postRotate(180f)
                }

                ExifInterface.ORIENTATION_FLIP_VERTICAL -> {
                    transformationMatrix.postScale(1f, -1f)
                }

                ExifInterface.ORIENTATION_TRANSPOSE -> {
                    /* TODO: Transpose the image Matrix */
                }

                ExifInterface.ORIENTATION_ROTATE_90 -> {
                    transformationMatrix.postRotate(90f)
                }

                ExifInterface.ORIENTATION_TRANSVERSE -> {
                    /* TODO: Flip horizontally the image Matrix, then transpose it */
                }

                ExifInterface.ORIENTATION_ROTATE_270 -> {
                    transformationMatrix.postRotate(270f)
                }
            }

            return Bitmap.createBitmap(
                bitmap,
                xStart,
                yStart,
                size,
                size,
                transformationMatrix,
                true
            )
        }

        fun bitmapToCache(id: String, bitmap: Bitmap): File {
            val file = File.createTempFile("bitmap_$id", ".jpg")
            file.outputStream().use {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 85, it)
                it.flush()
            }
            return file
        }

        fun bitmapFromCache(file: File): Bitmap {
            file.inputStream().use {
                return BitmapFactory.decodeStream(it)
            }
        }
    }
}
