package eu.steffo.twom.composables.avatar

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import eu.steffo.twom.composables.matrix.LocalSession

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
    val session = LocalSession.current
    var avatarUrl by rememberSaveable { mutableStateOf<String?>(null) }

    LaunchedEffect(session, userId) GetAvatarUrl@{
        if (session == null) {
            Log.d("AvatarUser", "Not doing anything, session is null.")
            return@GetAvatarUrl
        }

        if (userId.isEmpty()) {
            Log.d("AvatarUser", "Not doing anything, userId is empty.")
            return@GetAvatarUrl
        }

        Log.d("AvatarUser", "Retrieving avatar url for: $userId...")
        avatarUrl = session.profileService().getAvatarUrl(userId).getOrNull()
        Log.d("AvatarUser", "Retrieved avatar url for $userId: $avatarUrl")
    }

    AvatarURL(
        modifier = modifier,
        url = avatarUrl,
        fallbackText = fallbackText,
        contentDescription = contentDescription,
        alpha = alpha,
    )
}
