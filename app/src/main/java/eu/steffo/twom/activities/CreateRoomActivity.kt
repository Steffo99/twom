package eu.steffo.twom.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContract
import eu.steffo.twom.composables.createroom.CreateRoomScaffold


data class CreateRoomActivityResult(
    val name: String,
    val description: String,
    val avatarUri: String?,
)


class CreateRoomActivity : ComponentActivity() {
    companion object {
        const val NAME_EXTRA = "name"
        const val DESCRIPTION_EXTRA = "description"
        const val AVATAR_EXTRA = "avatar"
    }

    class Contract : ActivityResultContract<Unit, CreateRoomActivityResult?>() {
        override fun createIntent(context: Context, input: Unit): Intent {
            return Intent(context, CreateRoomActivity::class.java)
        }

        override fun parseResult(resultCode: Int, intent: Intent?): CreateRoomActivityResult? {
            return when (resultCode) {
                RESULT_OK -> CreateRoomActivityResult(
                    name = intent!!.getStringExtra(NAME_EXTRA)!!,
                    description = intent.getStringExtra(DESCRIPTION_EXTRA)!!,
                    avatarUri = intent.getStringExtra(AVATAR_EXTRA),
                )

                else -> null
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent { CreateRoomScaffold() }
    }
}