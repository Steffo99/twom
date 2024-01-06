package eu.steffo.twom.matrix.avatar

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.tooling.preview.Preview
import eu.steffo.twom.matrix.LocalSession
import org.matrix.android.sdk.api.failure.Failure
import java.io.File

@Composable
@Preview(widthDp = 40, heightDp = 40)
fun AvatarFromURL(
    modifier: Modifier = Modifier,
    url: String? = "",
    fallbackText: String = "?",
    contentDescription: String = "",
) {
    val session = LocalSession.current
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }

    LaunchedEffect(session, url) GetAvatar@{
        if (session == null) {
            Log.d("Avatar", "Not doing anything, session is null.")
            bitmap = null
            return@GetAvatar
        }
        if (url == null) {
            Log.d("Avatar", "URL is null, not downloading anything.")
            bitmap = null
            return@GetAvatar
        }
        if (url.isEmpty()) {
            Log.d("Avatar", "URL is a zero-length string, not downloading anything.")
            bitmap = null
            return@GetAvatar
        }

        Log.d("Avatar", "Downloading avatar at: $url")
        lateinit var avatarFile: File
        try {
            avatarFile = session.fileService().downloadFile(
                fileName = "avatar",
                url = url,
                mimeType = null,
                elementToDecrypt = null,
            )
        } catch (f: Failure.OtherServerError) {
            Log.e("Avatar", "Unable to download avatar at: $url", f)
            return@GetAvatar
        }

        // TODO: Should I check the MIME type? And the size of the image?
        Log.d("Avatar", "File for $url is: $avatarFile")
        bitmap = BitmapFactory.decodeFile(avatarFile.absolutePath)
    }

    if (bitmap == null) {
        AvatarFromDefault(
            modifier = modifier,
            fallbackText = fallbackText,
            contentDescription = contentDescription
        )
    } else {
        AvatarFromImageBitmap(
            modifier = modifier,
            bitmap = bitmap!!.asImageBitmap(),
            contentDescription = contentDescription,
        )
    }
}