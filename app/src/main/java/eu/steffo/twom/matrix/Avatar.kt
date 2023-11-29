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
    url: String,
    contentDescription: String,
) {
    var avatar by remember { mutableStateOf<ImageBitmap?>(null) }

    LaunchedEffect(session, url) GetAvatar@{
        Log.d("Avatar", "Downloading avatar at: $url")
        val avatarFile = session.fileService().downloadFile(
            fileName = "avatar",
            url = url,
            mimeType = null,
            elementToDecrypt = null,
        )
        // TODO: Should I check the MIME type? And the size of the image?
        Log.d("Avatar", "File for $url is: $avatarFile")
        val avatarBitmap = BitmapFactory.decodeFile(avatarFile.absolutePath)
        Log.d("Avatar", "Bitmap for $url is: $avatarBitmap")
        avatar = avatarBitmap.asImageBitmap()
    }

    Image(
        bitmap = if (avatar != null) avatar!! else TwoMMatrix.defaultAvatar,
        contentDescription = contentDescription
    )
}