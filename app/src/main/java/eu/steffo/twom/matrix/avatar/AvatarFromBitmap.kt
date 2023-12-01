package eu.steffo.twom.matrix.avatar

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.tooling.preview.Preview

@Composable
@Preview(widthDp = 40, heightDp = 40)
fun AvatarFromBitmap(
    modifier: Modifier = Modifier,
    bitmap: ImageBitmap? = null,
    fallbackText: String = "?",
    contentDescription: String = "",
) {
    if (bitmap == null) {
        AvatarFromDefault(
            modifier = modifier,
            fallbackText = fallbackText,
            contentDescription = contentDescription,
        )
    } else {
        Image(
            modifier = modifier,
            bitmap = bitmap,
            contentDescription = contentDescription,
        )
    }
}