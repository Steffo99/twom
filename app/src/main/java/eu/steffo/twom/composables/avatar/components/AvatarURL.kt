package eu.steffo.twom.composables.avatar.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.tooling.preview.Preview
import eu.steffo.twom.composables.avatar.effects.bitmapFromMatrixFile

@Composable
@Preview(name = "Regular", widthDp = 40, heightDp = 40)
@Preview(name = "Double font scale", widthDp = 40, heightDp = 40, fontScale = 2f)
@Preview(name = "Quadruple font scale", widthDp = 40, heightDp = 40, fontScale = 4f)
fun AvatarURL(
    modifier: Modifier = Modifier,
    url: String? = "",
    fallbackText: String? = null,
    contentDescription: String = "",
    alpha: Float = 1.0f,
) {
    AvatarImage(
        modifier = modifier,
        bitmap = bitmapFromMatrixFile(url)?.asImageBitmap(),
        fallbackText = fallbackText,
        contentDescription = contentDescription,
        alpha = alpha,
    )
}