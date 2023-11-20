package eu.steffo.twom

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
import eu.steffo.twom.matrix.TwoMMatrix
import eu.steffo.twom.ui.login.LoginActivity
import eu.steffo.twom.ui.theme.TwoMTheme


@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    private lateinit var loginLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.hide()

        TwoMMatrix.initMatrix(applicationContext)
        TwoMMatrix.tryInitSessionFromStorage()

        loginLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            Log.i("Garasauto", "Garaso")
        }
    }

    override fun onStart() {
        super.onStart()

        setContent {
            TwoMTheme {
                Scaffold(
                    topBar = {
                        CenterAlignedTopAppBar (
                            title = { Text(LocalContext.current.getString(R.string.app_name)) }
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
                            Text("→")
                        }
                    }
                }
            }
        }
    }
}
