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

        loginLauncher =
            registerForActivityResult(
                ActivityResultContracts.StartActivityForResult(),
                this::handleLoginResult
            )

        // This calls recompose() by itself
        openLastSession()
    }

    private fun openLastSession() {
        session = TwoMMatrix.matrix.authenticationService().getLastAuthenticatedSession()
        session?.open()
        recompose()
    }

    private fun recompose() {
        Log.d("Main", "Recomposing...")
        setContent {
            MatrixActivityScaffold(
                onClickLogin = this::onClickLogin,
                onClickLogout = this::onClickLogout,
                session = session,
            )
        }
    }

    private fun onClickLogin() {
        Log.d("Main", "Clicked login, launching login activity...")
        loginLauncher.launch(Intent(applicationContext, LoginActivity::class.java))
    }

    private fun handleLoginResult(result: ActivityResult) {
        when (result.resultCode) {
            RESULT_OK -> {
                Log.d(
                    "Main",
                    "Login activity returned a successful result, trying to get session..."
                )
                openLastSession()
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
            recompose()
        }
    }
}
