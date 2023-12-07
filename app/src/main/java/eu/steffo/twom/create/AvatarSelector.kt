package eu.steffo.twom.create

import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import eu.steffo.twom.matrix.avatar.AvatarFromImageBitmap

@Composable
@Preview(widthDp = 40, heightDp = 40)
fun AvatarSelector(
    modifier: Modifier = Modifier,
    onSelectAvatar: (bitmap: Bitmap) -> Unit = {},
) {
    val context = LocalContext.current
    val resolver = context.contentResolver

    var selection by remember { mutableStateOf<Bitmap?>(null) }

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) ImageSelect@{
            it ?: return@ImageSelect

            val rawBitmap = ImageHandler.getRawBitmap(resolver, it) ?: return@ImageSelect
            val orientation = ImageHandler.getOrientation(resolver, it) ?: return@ImageSelect

            val correctedBitmap = ImageHandler.squareAndOrient(rawBitmap, orientation)

            selection = correctedBitmap
            onSelectAvatar(correctedBitmap)
        }

    Box(
        modifier = modifier
            .clickable {
                launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }
    ) {
        AvatarFromImageBitmap(
            bitmap = selection?.asImageBitmap(),
        )
    }
}