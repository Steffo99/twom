package eu.steffo.twom.create

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import eu.steffo.twom.R
import eu.steffo.twom.matrix.avatar.AvatarFromBitmap
import eu.steffo.twom.theme.TwoMPadding

@Composable
@Preview
fun CreateActivityContent(
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    var name by rememberSaveable { mutableStateOf("") }
    var avatarUri by rememberSaveable { mutableStateOf<Uri?>(null) }
    var avatarBitmap by remember { mutableStateOf<ImageBitmap?>(null) }

    // val avatarBitmap = if(avatarUri != null) BitmapFactory.decodeFile(avatarUri.toString()).asImageBitmap() else null
    val avatarSelectLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) {
            avatarUri = it
            if (it == null) {
                avatarBitmap = null
            } else {
                // Open two streams...
                // One to read the EXIF metadata from:
                val exifStream = context.contentResolver.openInputStream(it)
                // One to read the image data itself from:
                val bitmapStream = context.contentResolver.openInputStream(it)

                if (exifStream != null && bitmapStream != null) {
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
                    val transformationMatrix: Matrix = Matrix()

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

                    avatarBitmap = croppedBitmap.asImageBitmap()
                }
            }
        }

    Column(modifier) {
        Row(TwoMPadding.base) {
            val avatarContentDescription = stringResource(R.string.create_avatar_label)
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .clickable {
                        avatarSelectLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                    }
                    .semantics {
                        this.contentDescription = avatarContentDescription
                    }
            ) {
                AvatarFromBitmap(
                    bitmap = avatarBitmap,
                )
            }

            TextField(
                modifier = Modifier
                    .height(60.dp)
                    .padding(start = 10.dp)
                    .fillMaxWidth(),
                label = {
                    Text(stringResource(R.string.create_name_label))
                },
                value = name,
                onValueChange = { name = it }
            )
        }
    }
}