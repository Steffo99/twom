package eu.steffo.twom.activities

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContract
import eu.steffo.twom.composables.configureroom.ConfigureRoomScaffold


class ConfigureRoomActivity : ComponentActivity() {
    companion object {
        const val NAME_EXTRA = "name"
        const val DESCRIPTION_EXTRA = "description"
        const val AVATAR_EXTRA = "avatar"
    }

    data class Configuration(
        val name: String,
        val description: String,
        val avatarUri: Uri?,
    ) {
        fun toIntent(): Intent {
            val intent = Intent()
            intent.putExtra(NAME_EXTRA, this.name)
            intent.putExtra(DESCRIPTION_EXTRA, this.description)
            if (this.avatarUri != null) {
                intent.putExtra(AVATAR_EXTRA, this.avatarUri.toString())
            }
            return intent
        }

        fun <A> toIntent(context: Context, klass: Class<A>): Intent {
            val intent = Intent(context, klass)
            intent.putExtra(NAME_EXTRA, this.name)
            intent.putExtra(DESCRIPTION_EXTRA, this.description)
            if (this.avatarUri != null) {
                intent.putExtra(AVATAR_EXTRA, this.avatarUri.toString())
            }
            return intent
        }

        companion object {
            fun fromIntent(intent: Intent): Configuration? {
                val name = intent.getStringExtra(NAME_EXTRA) ?: return null
                val description = intent.getStringExtra(DESCRIPTION_EXTRA) ?: return null
                val avatarString = intent.getStringExtra(AVATAR_EXTRA)
                val avatarUri = if (avatarString != null) Uri.parse(avatarString) else null

                return Configuration(
                    name = name,
                    description = description,
                    avatarUri = avatarUri,
                )
            }
        }
    }

    class CreateContract : ActivityResultContract<Unit, Configuration?>() {
        override fun createIntent(context: Context, input: Unit): Intent {
            return Intent(context, ConfigureRoomActivity::class.java)
        }

        override fun parseResult(resultCode: Int, intent: Intent?): Configuration? {
            return when (resultCode) {
                RESULT_OK -> Configuration.fromIntent(intent!!)
                else -> null
            }
        }
    }

    class EditContract : ActivityResultContract<Configuration, Configuration?>() {
        override fun createIntent(context: Context, input: Configuration): Intent {
            return input.toIntent(context, ConfigureRoomActivity::class.java)
        }

        override fun parseResult(resultCode: Int, intent: Intent?): Configuration? {
            return when (resultCode) {
                RESULT_OK -> Configuration.fromIntent(intent!!)
                else -> null
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val configuration = Configuration.fromIntent(intent)

        setContent {
            ConfigureRoomScaffold(
                initialConfiguration = configuration,
            )
        }
    }
}