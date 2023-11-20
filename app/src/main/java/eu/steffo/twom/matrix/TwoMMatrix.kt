package eu.steffo.twom.matrix

import TwoMRoomDisplayNameFallbackProvider
import android.content.Context
import androidx.compose.runtime.currentComposer
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
        private set

    fun tryInitSessionFromStorage() {
        val lastSession = matrix?.authenticationService()?.getLastAuthenticatedSession()
        if(lastSession != null) {
            session = lastSession
        }
    }

    // TODO: Does this throw an error if the session is already open?
    fun openSession() {
        val currentSession = session ?: throw SessionNotInitializedError()
        currentSession.open()
        currentSession.syncService().startSync(true)
    }

    // TODO: Does this throw an error if the session is already closed?
    fun closeSession() {
        val currentSession = session ?: throw SessionNotInitializedError()
        currentSession.close()
        currentSession.syncService().stopSync()
    }

    fun clearSession() {
        closeSession()
        session = null
    }
}
