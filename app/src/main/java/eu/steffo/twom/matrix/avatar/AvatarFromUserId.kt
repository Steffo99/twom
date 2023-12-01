package eu.steffo.twom.matrix.avatar

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import eu.steffo.twom.matrix.LocalSession

@Composable
@Preview(widthDp = 40, heightDp = 40)
fun AvatarFromUserId(
    modifier: Modifier = Modifier,
    userId: String = "",
    contentDescription: String = "",
) {
    val session = LocalSession.current
    var avatarUrl by rememberSaveable { mutableStateOf<String?>(null) }

    LaunchedEffect(session, userId) GetAvatarUrl@{
        if (session == null) {
            Log.d("UserAvatar", "Not doing anything, session is null.")
            return@GetAvatarUrl
        }
        if (userId.isEmpty()) {
            Log.d("UserAvatar", "Not doing anything, userId is empty.")
            return@GetAvatarUrl
        }
        Log.d("UserAvatar", "Retrieving avatar url for: $userId...")
        avatarUrl = session.profileService().getAvatarUrl(userId).getOrNull()
        Log.d("UserAvatar", "Retrieved avatar url for $userId: $avatarUrl")
    }

    if (avatarUrl == null) {
        AvatarFromDefault(
            modifier = modifier,
            contentDescription = contentDescription,
        )
    } else {
        AvatarFromURL(
            modifier = modifier,
            url = avatarUrl!!,
            contentDescription = contentDescription,
        )
    }
}