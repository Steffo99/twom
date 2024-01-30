package eu.steffo.twom.composables.avatar

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import eu.steffo.twom.composables.matrix.LocalSession
import eu.steffo.twom.utils.BitmapUtilities
import kotlinx.coroutines.CancellationException
import org.matrix.android.sdk.api.util.md5
import java.io.File

private const val TAG = "bitmapFromMatrixFile"

@Composable
fun bitmapFromMatrixFile(url: String? = null): Bitmap? {
    val session = LocalSession.current
    val resolver = LocalContext.current.contentResolver

    var bitmap by remember { mutableStateOf<Bitmap?>(null) }

    Log.v(TAG, "Compositing: $url")

    // LaunchedEffect does not behave as expected: sometimes, the effect is cancelled, but not relaunched!
    LaunchedEffect(session, url) Fetch@{
        Log.v(TAG, "Launching: $url")

        if (session == null) {
            Log.d(TAG, "Session is null, clearing bitmap.")
            bitmap = null
            return@Fetch
        }
        if (url == null) {
            Log.d(TAG, "URL is null, clearing bitmap.")
            bitmap = null
            return@Fetch
        }
        if (url.isEmpty()) {
            Log.d(TAG, "URL is a zero-length string, clearing bitmap.")
            bitmap = null
            return@Fetch
        }

        Log.i(TAG, "Downloading file at: $url")
        lateinit var avatarFile: File
        try {
            avatarFile = session.fileService().downloadFile(
                fileName = url.md5(),
                url = url,
                mimeType = null,
                elementToDecrypt = null,
            )
        } catch (e: CancellationException) {
            // This makes sure no corrupt image is displayed, at least
            Log.i(TAG, "Cancelled download of file at: $url", e)
            bitmap = null
            return@Fetch
        } catch (e: Throwable) {
            Log.e(TAG, "Unable to download file at: $url", e)
            bitmap = null
            return@Fetch
        }

        Log.d(TAG, "File for $url is: $avatarFile")

        bitmap = BitmapUtilities.getCorrectedBitmap(resolver, avatarFile.toUri())
    }

    return bitmap
}