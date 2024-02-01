package eu.steffo.twom.main.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import eu.steffo.twom.main.components.MainScaffold
import eu.steffo.twom.matrix.utils.TwoMMatrix
import kotlinx.coroutines.launch
import org.matrix.android.sdk.api.session.Session


private const val TAG = "MainActivity"


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initSession()
    }

    override fun onDestroy() {
        super.onDestroy()

        deinitSession()
    }

    private class SessionChangeListener(private val resetContent: () -> Unit) : Session.Listener {
        override fun onSessionStarted(session: Session) {
            Log.d(TAG, "Session has started!")
            resetContent()
        }

        override fun onSessionStopped(session: Session) {
            Log.d(TAG, "Session has stopped!")
            resetContent()
        }
    }

    private fun initSession() {
        Log.i(TAG, "Initializing session...")
        TwoMMatrix.ensureMatrix(applicationContext)
        TwoMMatrix.setupSession()
        TwoMMatrix.session?.addListener(SessionChangeListener { resetContent() })
    }

    private fun deinitSession() {
        Log.i(TAG, "Deinitializing session...")
        TwoMMatrix.teardownSession()
    }

    private fun resetContent() {
        Log.v(TAG, "Recomposing...")
        setContent {
            MainScaffold(
                session = TwoMMatrix.session,
                processLogin = {
                    initSession()
                },
                processLogout = {
                    lifecycleScope.launch {
                        TwoMMatrix.session?.signOutService()?.signOut(true)
                    }
                },
            )
        }
    }
}
