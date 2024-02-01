package eu.steffo.twom.avatar.utils

import android.graphics.Bitmap
import android.graphics.Matrix
import androidx.exifinterface.media.ExifInterface

fun Bitmap.squareAndOrient(
    orientation: Int = ExifInterface.ORIENTATION_NORMAL
): Bitmap {
    // Determine the starting points and the size to crop the image to a 1:1 square
    val xStart: Int
    val yStart: Int
    val size: Int
    if (this.width > this.height) {
        yStart = 0
        xStart = (this.width - this.height) / 2
        size = this.height
    } else {
        xStart = 0
        yStart = (this.height - this.width) / 2
        size = this.width
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
        this,
        xStart,
        yStart,
        size,
        size,
        transformationMatrix,
        true
    )
}