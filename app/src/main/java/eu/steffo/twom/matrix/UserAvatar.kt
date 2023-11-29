package eu.steffo.twom.matrix

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import org.matrix.android.sdk.api.session.Session

@Composable
fun UserAvatar(
    session: Session,
    userId: String,
    contentDescription: String,
) {
    var avatarUrl by rememberSaveable { mutableStateOf<String?>(null) }

    LaunchedEffect(session, userId) GetAvatarUrl@{
        Log.d("UserAvatar", "Retrieving avatar url for: $userId...")
        avatarUrl = session.profileService().getAvatarUrl(userId).getOrNull()
        Log.d("UserAvatar", "Retrieved avatar url for $userId: $avatarUrl")
    }

    if (avatarUrl == null) {
        Image(
            bitmap = TwoMMatrix.defaultAvatar,
            contentDescription = contentDescription,
        )
    } else {
        Avatar(
            session = session,
            url = avatarUrl!!,
            contentDescription = contentDescription,
        )
    }
}