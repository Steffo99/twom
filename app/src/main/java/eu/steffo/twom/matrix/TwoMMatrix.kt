package eu.steffo.twom.matrix

import TwoMRoomDisplayNameFallbackProvider
import android.content.Context
import org.matrix.android.sdk.api.Matrix
import org.matrix.android.sdk.api.MatrixConfiguration
import org.matrix.android.sdk.api.session.Session


object TwoMMatrix {
    var matrix: Matrix? = null
        private set

    fun initMatrix(context: Context) {
        if(matrix != null) {
            throw MatrixAlreadyInitializedError()
        }
        matrix = Matrix(
            context = context.applicationContext,
            matrixConfiguration = MatrixConfiguration(
                applicationFlavor = "TwoM",
                roomDisplayNameFallbackProvider = TwoMRoomDisplayNameFallbackProvider(context.applicationContext)
            )
        )
    }

    var session: Session? = null
        set(value) {
            if (field != null) {
                closeSession()
            }
            field = value
            if (field != null) {
                openSession()
            }
        }

    fun tryInitSessionFromStorage() {
        val lastSession = matrix?.authenticationService()?.getLastAuthenticatedSession()
        if(lastSession != null) {
            session = lastSession
        }
    }

    // TODO: Does this throw an error if the session is already open?
    private fun openSession() {
        val currentSession = session ?: throw SessionNotInitializedError()
        currentSession.open()
        currentSession.syncService().startSync(true)
    }

    // TODO: Does this throw an error if the session is already closed?
    private fun closeSession() {
        val currentSession = session ?: throw SessionNotInitializedError()
        currentSession.close()
        currentSession.syncService().stopSync()
    }
}
