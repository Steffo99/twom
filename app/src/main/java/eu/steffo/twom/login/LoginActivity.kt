package eu.steffo.twom.login

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent


class LoginActivity : ComponentActivity() {
    override fun onStart() {
        super.onStart()

        setContent {
            LoginActivityScaffold()
        }
    }
}
