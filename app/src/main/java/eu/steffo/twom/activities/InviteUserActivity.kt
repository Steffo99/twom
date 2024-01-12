package eu.steffo.twom.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContract
import androidx.compose.material3.Text


class InviteUserActivity : ComponentActivity() {
    companion object {
        const val USER_EXTRA = "user"
    }

    class Contract : ActivityResultContract<Unit, String?>() {
        override fun createIntent(context: Context, input: Unit): Intent {
            return Intent(context, CreateRoomActivity::class.java)
        }

        override fun parseResult(resultCode: Int, intent: Intent?): String? {
            return when (resultCode) {
                RESULT_OK -> intent!!.getStringExtra(USER_EXTRA)
                else -> null
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent { Text("Garasauto Prime") }
    }
}