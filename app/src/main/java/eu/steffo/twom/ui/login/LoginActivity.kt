package eu.steffo.twom.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import eu.steffo.twom.ui.homeserver.SelectHomeserverActivity


class LoginActivity : ComponentActivity() {
    private lateinit var homeserverLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        homeserverLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val selectedHomeserver =
                it.data?.getStringExtra(SelectHomeserverActivity.HOMESERVER_EXTRA_KEY)
            Log.d("LoginActivity", "Selected homeserver: $selectedHomeserver")
            setContent {
                LoginActivityScaffold(
                    selectedHomeserver = selectedHomeserver,
                    onSelectHomeserver = {
                        homeserverLauncher.launch(
                            Intent(
                                applicationContext,
                                SelectHomeserverActivity::class.java
                            )
                        )
                    },
                )
            }
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
