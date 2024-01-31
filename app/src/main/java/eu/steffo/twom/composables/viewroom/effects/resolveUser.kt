package eu.steffo.twom.composables.viewroom.effects

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import eu.steffo.twom.composables.matrix.LocalSession
import kotlinx.coroutines.CancellationException
import org.matrix.android.sdk.api.session.user.model.User

private const val TAG = "resolveUser"

@Composable
fun resolveUser(userId: String?): User? {
    val session = LocalSession.current

    var user by remember { mutableStateOf<User?>(null) }

    LaunchedEffect(session, userId) Fetch@{
        if (session == null) {
            Log.d(TAG, "Session is null, clearing user...")
            user = null
            return@Fetch
        }
        if (userId == null) {
            Log.d(TAG, "userId is null, clearing user...")
            user = null
            return@Fetch
        }

        Log.i(TAG, "Resolving user: $userId")
        try {
            user = session.userService().resolveUser(userId)
        } catch (e: CancellationException) {
            // This makes sure no corrupt image is displayed, at least
            Log.d(TAG, "Cancelled resolution of user: $userId", e)
            user = null
            return@Fetch
        } catch (e: Throwable) {
            Log.e(TAG, "Unable to resolve user: $userId", e)
            user = null
            return@Fetch
        }

        Log.d(TAG, "Resolved user: $userId")
    }

    return user
}
