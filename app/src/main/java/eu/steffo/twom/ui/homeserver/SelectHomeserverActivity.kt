package eu.steffo.twom.ui.homeserver

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent


class SelectHomeserverActivity : ComponentActivity() {
    companion object {
        const val HOMESERVER_EXTRA_KEY = "homeserver"
    }

    override fun onStart() {
        super.onStart()
        setContent {
            SelectHomeserverScaffold(
                onBack = {
                    setResult(RESULT_CANCELED)
                    finish()
                },
                onComplete = {
                    val result = Intent()
                    result.putExtra(HOMESERVER_EXTRA_KEY, it)
                    setResult(RESULT_OK, result)
                    finish()
                }
            )
        }
    }
}
