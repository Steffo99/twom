package eu.steffo.twom.matrix.utils

import android.content.Context
import android.util.Log
import org.matrix.android.sdk.api.Matrix
import org.matrix.android.sdk.api.MatrixConfiguration
import org.matrix.android.sdk.api.session.Session

private const val TAG = "TwoMMatrix"

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
            Log.i(TAG, "Initializing Matrix...")
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

    val session: Session?
        get() = matrix.authenticationService().getLastAuthenticatedSession()

    fun setupSession() {
        Log.d(TAG, "Attempting to setup session...")
        val session = this.session

        if (session == null) {
            Log.d(TAG, "Session is null, nothing to setup.")
            return
        }

        Log.d(TAG, "Session is: $session")

        Log.i(TAG, "Opening session: $session")
        session.open()

        Log.i(TAG, "Starting to sync with session: $session")
        session.syncService().startSync(true)
    }

    fun teardownSession() {
        Log.d(TAG, "Attempting to teardown session...")
        val session = this.session

        if (session == null) {
            Log.d(TAG, "Session is null, nothing to teardown.")
            return
        }

        Log.d(TAG, "Session is: $session")

        Log.i(TAG, "Stopping to sync with session: $session")
        session.syncService().stopSync()

        Log.i(TAG, "Closing session: $session")
        session.close()
    }

    const val ROOM_TYPE = "eu.steffo.twom.happening"

    const val RSVP_STATE_TYPE = "eu.steffo.twom.rsvp"

    const val RSVP_STATE_ANSWER_FIELD = "answer"

    const val RSVP_STATE_COMMENT_FIELD = "comment"
}