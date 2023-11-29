package eu.steffo.twom.matrix

import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import org.matrix.android.sdk.api.session.Session

@Composable
fun Avatar(
    session: Session,
    userId: String,
    contentDescription: String,
) {
    var avatar by remember { mutableStateOf<ImageBitmap?>(null) }
    var fetched by remember { mutableStateOf(false) }

    LaunchedEffect(session, userId) GetAvatar@{
        Log.d("Avatar", "Trying to retrieve the avatar url for $userId...")
        val avatarUrl = session.profileService().getAvatarUrl(userId)
        Log.d("Avatar", "Avatar URL for $userId is: $avatarUrl")
        val avatarFile = session.fileService().downloadFile(
            fileName = "avatar",
            url = avatarUrl.getOrNull(),
            mimeType = null,
            elementToDecrypt = null,
        )
        // TODO: Should I check the MIME type? And the size of the image?
        Log.d("Avatar", "Avatar file for $userId is: $avatarFile")
        val avatarBitmap = BitmapFactory.decodeFile(avatarFile.absolutePath)
        Log.d("Avatar", "Avatar bitmap for $userId is: $avatarBitmap")
        avatar = avatarBitmap.asImageBitmap()
        fetched = true
    }

    Image(
        bitmap = if (fetched && avatar != null) avatar!! else TwoMMatrix.defaultAvatar,
        contentDescription = contentDescription
    )
}