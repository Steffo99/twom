package eu.steffo.twom.matrix

import android.content.Context
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

    private fun isMatrixInitialized(): Boolean {
        return this::matrix.isInitialized
    }

    /**
     * Make sure the [matrix] object is available, constructing it if it isn't initialized.
     *
     * Uses the passed [Context] to access the [application context][Context.getApplicationContext], which is required by the SDK provided by the Matrix Foundation.
     */
    fun ensureMatrix(context: Context): Matrix? {
        if (!isMatrixInitialized()) {
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
}
