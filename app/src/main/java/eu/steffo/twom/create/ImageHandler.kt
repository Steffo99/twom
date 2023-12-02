package eu.steffo.twom.create

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import androidx.exifinterface.media.ExifInterface

class ImageHandler {
    companion object {
        fun uriToBitmap(contentResolver: ContentResolver, uri: Uri): Bitmap? {
            // Open two streams...
            // One to read the EXIF metadata from:
            val exifStream = contentResolver.openInputStream(uri)
            // One to read the image data itself from:
            val bitmapStream = contentResolver.openInputStream(uri)

            if (exifStream == null || bitmapStream == null) {
                return null
            }

            // Use the EXIF metadata to determine the orientation of the image
            val exifInterface = ExifInterface(exifStream)
            val orientation =
                exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1)
            exifStream.close()

            // Parse the image data as-is
            val originalBitmap = BitmapFactory.decodeStream(bitmapStream)
            bitmapStream.close()

            // Determine the starting points and the size to crop the image to a 1:1 square
            val xStart: Int
            val yStart: Int
            val size: Int
            if (originalBitmap.width > originalBitmap.height) {
                yStart = 0
                xStart = (originalBitmap.width - originalBitmap.height) / 2
                size = originalBitmap.height
            } else {
                xStart = 0
                yStart = (originalBitmap.height - originalBitmap.width) / 2
                size = originalBitmap.width
            }

            // Create a transformation matrix to rotate the bitmap based on the orientation
            val transformationMatrix = Matrix()

            // TODO: Make sure these transformations are valid
            when (orientation) {
                ExifInterface.ORIENTATION_FLIP_HORIZONTAL -> transformationMatrix.postScale(
                    -1f,
                    1f
                )

                ExifInterface.ORIENTATION_ROTATE_180 -> transformationMatrix.postRotate(180f)
                ExifInterface.ORIENTATION_FLIP_VERTICAL -> transformationMatrix.postScale(
                    1f,
                    -1f
                )

                ExifInterface.ORIENTATION_TRANSPOSE -> {/* TODO: Transpose the image Matrix */
                }

                ExifInterface.ORIENTATION_ROTATE_90 -> transformationMatrix.postRotate(90f)
                ExifInterface.ORIENTATION_TRANSVERSE -> {/* TODO: Flip horizontally the image Matrix, then transpose it */
                }

                ExifInterface.ORIENTATION_ROTATE_270 -> transformationMatrix.postRotate(270f)
            }

            // Crop the bitmap
            val croppedBitmap = Bitmap.createBitmap(
                originalBitmap,
                xStart,
                yStart,
                size,
                size,
                transformationMatrix,
                true
            )

            return croppedBitmap
        }
    }
}