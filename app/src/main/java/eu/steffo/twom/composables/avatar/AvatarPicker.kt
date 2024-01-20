package eu.steffo.twom.composables.avatar

import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import eu.steffo.twom.utils.BitmapUtilities

@OptIn(ExperimentalFoundationApi::class)
@Composable
@Preview(widthDp = 40, heightDp = 40)
fun AvatarPicker(
    modifier: Modifier = Modifier,
    fallbackText: String = "?",
    value: Bitmap? = null,
    onPick: (bitmap: Bitmap?) -> Unit = {},
    alpha: Float = 1.0f,
) {
    val context = LocalContext.current
    val resolver = context.contentResolver

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) ImageSelect@{
            it ?: return@ImageSelect

            val rawBitmap = BitmapUtilities.getRawBitmap(resolver, it) ?: return@ImageSelect
            val orientation = BitmapUtilities.getOrientation(resolver, it) ?: return@ImageSelect

            val correctedBitmap = BitmapUtilities.squareAndOrient(rawBitmap, orientation)

            onPick(correctedBitmap)
        }

    Box(
        modifier = modifier
            .combinedClickable(
                onClick = { launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) },
                onLongClick = { onPick(null) },
            )
    ) {
        AvatarImage(
            bitmap = value?.asImageBitmap(),
            fallbackText = fallbackText,
            alpha = alpha,
        )
    }
}