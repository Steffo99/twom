package eu.steffo.twom.global

import TwoMRoomDisplayNameFallbackProvider
import android.content.Context
import org.matrix.android.sdk.api.Matrix
import org.matrix.android.sdk.api.MatrixConfiguration
import org.matrix.android.sdk.api.session.Session


object TwoMMatrix {
    lateinit var matrix: Matrix
        private set

    fun initMatrix(context: Context) {
        matrix = Matrix(
            context = context.applicationContext,
            matrixConfiguration = MatrixConfiguration(
                applicationFlavor = "TwoM",
                roomDisplayNameFallbackProvider = TwoMRoomDisplayNameFallbackProvider(context.applicationContext)
            )
        )
    }

    var session: Session? = null

    fun initSessionFromStorage() {
        val lastSession = matrix.authenticationService().getLastAuthenticatedSession()
        if(lastSession != null) {
            session = lastSession
        }
    }

    fun openSession() {
        // FIXME: Possible race condition here?
        session?.open()
        session?.syncService()?.startSync(true)
    }
}
