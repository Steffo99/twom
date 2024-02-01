package eu.steffo.twom.avatar.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import eu.steffo.twom.avatar.effects.avatarUrlFromUserId

@Composable
@Preview(name = "Regular", widthDp = 40, heightDp = 40)
@Preview(name = "Double font scale", widthDp = 40, heightDp = 40, fontScale = 2f)
@Preview(name = "Quadruple font scale", widthDp = 40, heightDp = 40, fontScale = 4f)
fun AvatarUserId(
    modifier: Modifier = Modifier,
    userId: String = "",
    fallbackText: String = "?",
    contentDescription: String = "",
    alpha: Float = 1.0f,
) {
    AvatarURL(
        modifier = modifier,
        url = avatarUrlFromUserId(userId),
        fallbackText = fallbackText,
        contentDescription = contentDescription,
        alpha = alpha,
    )
}
