package eu.steffo.twom.composables.avatar

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview

@Composable
@Preview(widthDp = 40, heightDp = 40)
fun AvatarImage(
    modifier: Modifier = Modifier,
    bitmap: ImageBitmap? = null,
    fallbackText: String? = null,
    contentDescription: String = "",
    alpha: Float = 1.0f,
) {
    if (bitmap == null) {
        AvatarEmpty(
            modifier = modifier
                .semantics {
                    this.contentDescription = contentDescription
                },
            text = fallbackText,
            alpha = alpha,
        )
    } else {
        Image(
            modifier = modifier,
            bitmap = bitmap,
            contentDescription = contentDescription,
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center,
            alpha = alpha,
        )
    }
}