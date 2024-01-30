package eu.steffo.twom.composables.avatar.picker

import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import eu.steffo.twom.utils.BitmapUtilities

@OptIn(ExperimentalFoundationApi::class)
@Composable
@Preview(name = "Regular", widthDp = 40, heightDp = 40)
@Preview(name = "Double font scale", widthDp = 40, heightDp = 40, fontScale = 2f)
@Preview(name = "Quadruple font scale", widthDp = 40, heightDp = 40, fontScale = 4f)
fun AvatarPickerWrapbox(
    modifier: Modifier = Modifier,
    onPick: (bitmap: Bitmap?) -> Unit = {},
    contents: @Composable () -> Unit = {},
) {
    val context = LocalContext.current
    val resolver = context.contentResolver

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) ImageSelect@{
            it ?: return@ImageSelect
            val correctedBitmap = BitmapUtilities.getCorrectedBitmap(resolver, it)
            onPick(correctedBitmap)
        }

    Box(
        modifier = modifier
            .combinedClickable(
                onClick = { launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) },
                onLongClick = { onPick(null) },
            )
    ) {
        contents()
    }
}