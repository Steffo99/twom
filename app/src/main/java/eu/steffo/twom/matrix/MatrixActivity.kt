package eu.steffo.twom.matrix

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import eu.steffo.twom.R
import eu.steffo.twom.login.LoginActivity
import eu.steffo.twom.theme.TwoMTheme
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
                Log.d(this::class.qualifiedName, "LoginActivity has returned a result!")

                when (it.resultCode) {
                    RESULT_OK -> {
                        session =
                            TwoMMatrix.matrix.authenticationService().getLastAuthenticatedSession()

                        setContent {
                            TwoMTheme {
                                Scaffold(
                                    topBar = {
                                        CenterAlignedTopAppBar(
                                            title = {
                                                Text(LocalContext.current.getString(R.string.app_name))
                                            }
                                        )
                                    }
                                ) {
                                    Text(session?.myUserId ?: "No session", Modifier.padding(it))
                                }
                            }
                        }
                    }
                }
                if (it.resultCode == RESULT_CANCELED) {
                    return@HandleResult
                }
            }
    }

    override fun onStart() {
        super.onStart()

        setContent {
            TwoMTheme {
                Scaffold(
                    topBar = {
                        CenterAlignedTopAppBar(
                            title = {
                                Text(LocalContext.current.getString(R.string.app_name))
                            }
                        )
                    }
                ) {
                    Row(Modifier.padding(it)) {
                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                loginLauncher.launch(Intent(applicationContext, LoginActivity::class.java))
                            }
                        ) {
                            Text("plis login with matrics no scam 100%")
                        }
                    }
                }
            }
        }
    }
}
