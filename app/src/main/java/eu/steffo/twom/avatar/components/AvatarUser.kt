package eu.steffo.twom.avatar.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.matrix.android.sdk.api.session.user.model.User
import org.matrix.android.sdk.api.util.toMatrixItem

@Composable
@Preview(name = "Regular", widthDp = 40, heightDp = 40)
@Preview(name = "Double font scale", widthDp = 40, heightDp = 40, fontScale = 2f)
@Preview(name = "Quadruple font scale", widthDp = 40, heightDp = 40, fontScale = 4f)
fun AvatarUser(
    modifier: Modifier = Modifier,
    user: User? = null,
    contentDescription: String = "",
    alpha: Float = 1.0f,
) {
    AvatarURL(
        modifier = modifier,
        url = user?.avatarUrl,
        fallbackText = user?.toMatrixItem()?.firstLetterOfDisplayName(),
        contentDescription = contentDescription,
        alpha = alpha,
    )
}
