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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun Avatar(
    modifier: Modifier = Modifier,
    url: String? = "",
    contentDescription: String = "",
) {
    val session = LocalSession.current
    var avatar by remember { mutableStateOf<ImageBitmap?>(null) }

    LaunchedEffect(session, url) GetAvatar@{
        if (session == null) {
            Log.d("Avatar", "Not doing anything, session is null.")
            return@GetAvatar
        }
        if (url == null) {
            Log.d("Avatar", "URL is null, not downloading anything.")
            return@GetAvatar
        }
        if (url.isEmpty()) {
            Log.d("Avatar", "URL is a zero-length string, not downloading anything.")
            return@GetAvatar
        }
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

    if (session == null || url == null || avatar == null) {
        DefaultAvatar(
            modifier = modifier,
            contentDescription = contentDescription
        )
    } else {
        Image(
            modifier = modifier,
            bitmap = avatar!!,
            contentDescription = contentDescription,
        )
    }
}