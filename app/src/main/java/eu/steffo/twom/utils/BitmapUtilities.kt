package eu.steffo.twom.utils

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import androidx.exifinterface.media.ExifInterface
import java.io.File

class BitmapUtilities {
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
                    // Untested.
                    transformationMatrix.setValues(FloatArray(16) {
                        if (it == 0 || it == 6 || it == 9 || it == 15) {
                            1f
                        } else {
                            0f
                        }
                    })
                }

                ExifInterface.ORIENTATION_ROTATE_90 -> {
                    transformationMatrix.postRotate(90f)
                }

                ExifInterface.ORIENTATION_TRANSVERSE -> {
                    // Untested.
                    transformationMatrix.setValues(FloatArray(16) {
                        if (it == 3 || it == 5 || it == 10 || it == 12) {
                            1f
                        } else {
                            0f
                        }
                    })
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

        fun getCorrectedBitmap(resolver: ContentResolver, uri: Uri): Bitmap? {
            val rawBitmap = getRawBitmap(resolver, uri) ?: return null
            val orientation = getOrientation(resolver, uri) ?: return null
            return squareAndOrient(rawBitmap, orientation)
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
