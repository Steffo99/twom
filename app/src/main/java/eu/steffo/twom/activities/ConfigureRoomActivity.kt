package eu.steffo.twom.activities

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContract
import eu.steffo.twom.composables.createroom.CreateRoomScaffold


class ConfigureRoomActivity : ComponentActivity() {
    companion object {
        const val NAME_EXTRA = "name"
        const val DESCRIPTION_EXTRA = "description"
        const val AVATAR_EXTRA = "avatar"
    }

    data class Result(
        val name: String,
        val description: String,
        val avatarUri: Uri?,
    )

    class Contract : ActivityResultContract<Unit, Result?>() {
        override fun createIntent(context: Context, input: Unit): Intent {
            return Intent(context, ConfigureRoomActivity::class.java)
        }

        override fun parseResult(resultCode: Int, intent: Intent?): Result? {
            return when (resultCode) {
                RESULT_OK -> {
                    intent!!
                    val name = intent.getStringExtra(NAME_EXTRA)!!
                    val description = intent.getStringExtra(DESCRIPTION_EXTRA)!!
                    val avatar = intent.getStringExtra(AVATAR_EXTRA)

                    Result(
                        name = name,
                        description = description,
                        avatarUri = if (avatar != null) Uri.parse(avatar) else null,
                    )
                }

                else -> null
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent { CreateRoomScaffold() }
    }
}