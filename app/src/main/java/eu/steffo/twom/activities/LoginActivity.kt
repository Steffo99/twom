package eu.steffo.twom.activities

import android.content.Context
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContract
import eu.steffo.twom.composables.login.LoginScaffold


class LoginActivity : ComponentActivity() {
    class Contract : ActivityResultContract<Unit, Unit?>() {
        override fun createIntent(context: Context, input: Unit): Intent {
            return Intent(context, LoginActivity::class.java)
        }

        override fun parseResult(resultCode: Int, intent: Intent?): Unit? {
            return when (resultCode) {
                RESULT_OK -> Unit
                else -> null
            }
        }
    }

    override fun onStart() {
        super.onStart()

        setContent { LoginScaffold() }
    }
}
