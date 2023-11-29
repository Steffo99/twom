package eu.steffo.twom.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import eu.steffo.twom.login.LoginActivity
import eu.steffo.twom.matrix.TwoMMatrix
import kotlinx.coroutines.launch
import org.matrix.android.sdk.api.session.Session


class MainActivity : ComponentActivity() {
    private lateinit var loginLauncher: ActivityResultLauncher<Intent>

    private var session: Session? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        TwoMMatrix.ensureMatrix(applicationContext)
        TwoMMatrix.ensureDefaultAvatar(applicationContext)

        fetchLastSession()
        openSession()

        loginLauncher =
            registerForActivityResult(
                ActivityResultContracts.StartActivityForResult(),
                this::onLogin
            )

        resetContent()
    }

    override fun onDestroy() {
        super.onDestroy()

        closeSession()
    }

    private fun fetchLastSession() {
        Log.d("Main", "Fetching the last successfully authenticated session...")
        session = TwoMMatrix.matrix.authenticationService().getLastAuthenticatedSession()
    }

    private fun openSession() {
        Log.d("Main", "If possible, opening session: $session")
        session?.open()
    }

    private fun closeSession() {
        Log.d("Main", "If possible, closing session: $session")
        session?.close()
    }

    private fun onClickLogin() {
        Log.d("Main", "Clicked login, launching login activity...")
        loginLauncher.launch(Intent(applicationContext, LoginActivity::class.java))
    }

    private fun onLogin(result: ActivityResult) {
        Log.d("Main", "Received result from login activity: $result")
        when (result.resultCode) {
            RESULT_OK -> {
                Log.d(
                    "Main",
                    "Login activity returned a successful result, trying to get session..."
                )
                fetchLastSession()
                session?.open()
                resetContent()
            }

            else -> {
                Log.d("Main", "Login activity was cancelled.")
            }
        }
    }

    private fun onClickLogout() {
        lifecycleScope.launch {
            Log.d("Main", "Clicked logout, signing out...")
            session!!.signOutService().signOut(true)
            session = null
            Log.d("Main", "Done logging out, recomposing...")
            resetContent()
        }
    }

    private fun resetContent() {
        Log.d("Main", "Recomposing...")
        setContent {
            MatrixActivityScaffold(
                onClickLogin = this::onClickLogin,
                onClickLogout = this::onClickLogout,
                session = session,
            )
        }
    }
}
