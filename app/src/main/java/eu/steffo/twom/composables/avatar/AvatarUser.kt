package eu.steffo.twom.composables.avatar

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.matrix.android.sdk.api.session.user.model.User
import org.matrix.android.sdk.api.util.toMatrixItem

@Composable
@Preview(widthDp = 40, heightDp = 40)
fun AvatarUser(
    modifier: Modifier = Modifier,
    user: User? = null,
    fallbackText: String? = null,
    contentDescription: String = "",
) {
    AvatarURL(
        modifier = modifier,
        url = user?.avatarUrl,
        fallbackText = user?.toMatrixItem()?.firstLetterOfDisplayName(),
        contentDescription = contentDescription,
    )
}
