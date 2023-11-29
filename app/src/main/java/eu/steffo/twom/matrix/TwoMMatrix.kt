package eu.steffo.twom.matrix

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import eu.steffo.twom.R
import org.matrix.android.sdk.api.Matrix
import org.matrix.android.sdk.api.MatrixConfiguration


/**
 * Object containing the global state of the application.
 */
object TwoMMatrix {
    /**
     * The global [Matrix] object of the application.
     *
     * Most activities will expect this to be available.
     */
    lateinit var matrix: Matrix

    /**
     * Make sure the [matrix] object is available, constructing it if it isn't initialized.
     *
     * Uses the passed [Context] to access the [application context][Context.getApplicationContext], which is required by the SDK provided by the Matrix Foundation.
     */
    fun ensureMatrix(context: Context): Matrix? {
        if (!this::matrix.isInitialized) {
            Log.d("Matrix", "Initializing Matrix...")
            matrix = Matrix(
                context = context.applicationContext,
                matrixConfiguration = MatrixConfiguration(
                    applicationFlavor = "TwoM",
                    roomDisplayNameFallbackProvider = TwoMRoomDisplayNameFallbackProvider(context.applicationContext)
                )
            )
            return matrix
        }
        return null
    }

    /**
     * The avatar to default to when another one is not available.
     *
     * [Avatar] will expect this to be available.
     */
    lateinit var defaultAvatar: ImageBitmap

    /**
     * Make sure the [matrix] object is available, decoding it if it isn't initialized.
     *
     * Uses the passed [Context] to access the application resources.
     */
    fun ensureDefaultAvatar(context: Context): ImageBitmap? {
        if (!this::defaultAvatar.isInitialized) {
            Log.d("Matrix", "Initializing default avatar...")
            defaultAvatar =
                BitmapFactory.decodeResource(context.resources, R.drawable.avatar_default)
                    .asImageBitmap()
            return defaultAvatar
        }
        return null
    }
}
