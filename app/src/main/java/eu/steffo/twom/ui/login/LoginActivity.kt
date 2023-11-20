package eu.steffo.twom.ui.login

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import eu.steffo.twom.R
import eu.steffo.twom.ui.homeserver.SelectHomeserverActivity
import eu.steffo.twom.ui.theme.TwoMTheme


class LoginActivity : ComponentActivity() {
    private lateinit var homeserverLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        homeserverLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            Log.i("Garasauto", it.data?.getStringExtra(SelectHomeserverActivity.HOMESERVER_EXTRA_KEY) ?: "Undefined")
        }
    }

    override fun onStart() {
        super.onStart()

        setContent {
            LoginActivityScaffold(
                onSelectHomeserver = {
                    homeserverLauncher.launch(Intent(applicationContext, SelectHomeserverActivity::class.java))
                }
            )
        }
    }
}
