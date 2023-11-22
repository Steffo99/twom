package eu.steffo.twom.matrix

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.ExperimentalMaterial3Api
import eu.steffo.twom.login.LoginActivity
import org.matrix.android.sdk.api.session.Session


@OptIn(ExperimentalMaterial3Api::class)
class MatrixActivity : ComponentActivity() {
    private lateinit var loginLauncher: ActivityResultLauncher<Intent>

    private var session: Session? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.hide()

        TwoMMatrix.ensureMatrix(applicationContext)

        loginLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) HandleResult@{
                Log.d("Matrix", "LoginActivity has returned a result!")

                when (it.resultCode) {
                    RESULT_OK -> {
                        session =
                            TwoMMatrix.matrix.authenticationService().getLastAuthenticatedSession()

                        setContent {
                            MatrixActivityScaffold(
                                onClickLogin = this::onClickLogin,
                                session = session,
                            )
                        }
                    }
                }
            }
    }

    override fun onStart() {
        super.onStart()

        setContent {
            MatrixActivityScaffold(
                onClickLogin = this::onClickLogin
            )
        }
    }

    private fun onClickLogin() {
        loginLauncher.launch(Intent(applicationContext, LoginActivity::class.java))
    }
}
