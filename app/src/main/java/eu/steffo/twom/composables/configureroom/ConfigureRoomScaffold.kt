package eu.steffo.twom.composables.configureroom

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import eu.steffo.twom.activities.ConfigureRoomActivity
import eu.steffo.twom.composables.theme.TwoMTheme

@Composable
@Preview
fun CreateRoomScaffold() {
    val context = LocalContext.current
    val activity = context as Activity

    fun submitActivity(name: String, description: String, avatarUri: Uri?) {
        val resultIntent = Intent()
        resultIntent.putExtra(ConfigureRoomActivity.NAME_EXTRA, name)
        resultIntent.putExtra(ConfigureRoomActivity.DESCRIPTION_EXTRA, description)
        // Kotlin cannot use nullable types in Java interop generics
        if (avatarUri != null) {
            resultIntent.putExtra(ConfigureRoomActivity.AVATAR_EXTRA, avatarUri)
        }
        activity.setResult(ComponentActivity.RESULT_OK, resultIntent)
        activity.finish()
    }

    TwoMTheme {
        Scaffold(
            topBar = {
                CreateActivityTopBar()
            },
            content = {
                ConfigureRoomForm(
                    modifier = Modifier.padding(it),
                    onSubmit = { name: String, description: String, avatarUri: Uri? ->
                        submitActivity(name, description, avatarUri)
                    }
                )
            }
        )
    }
}