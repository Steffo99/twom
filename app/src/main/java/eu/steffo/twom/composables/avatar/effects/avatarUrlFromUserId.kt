package eu.steffo.twom.composables.avatar.effects

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import eu.steffo.twom.composables.matrix.LocalSession
import kotlinx.coroutines.CancellationException

private const val TAG = "avatarUrlFromUserId"

@Composable
fun avatarUrlFromUserId(userId: String): String? {
    val session = LocalSession.current

    var url by rememberSaveable { mutableStateOf<String?>(null) }

    LaunchedEffect(session, userId) Fetch@{
        if (session == null) {
            Log.d(TAG, "Session is null, clearing URL...")
            url = null
            return@Fetch
        }
        if (userId.isEmpty()) {
            Log.d(TAG, "UserID is empty, clearing URL...")
            url = null
            return@Fetch
        }

        Log.i(TAG, "Retrieving avatar URL for: $userId...")
        try {
            url = session.profileService().getAvatarUrl(userId).getOrNull()
        } catch (e: CancellationException) {
            Log.i(TAG, "Cancelled retrieval of avatar URL of: $userId", e)
            url = null
            return@Fetch
        } catch (e: Throwable) {
            Log.e(TAG, "Unable to retrieve avatar URL of: $userId", e)
            url = null
            return@Fetch
        }

        Log.d(TAG, "Avatar URL for $userId is: $url")
    }

    return url
}