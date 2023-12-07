package eu.steffo.twom.create

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class CreateActivity : ComponentActivity() {
    companion object {
        const val NAME_EXTRA = "name"
        const val DESCRIPTION_EXTRA = "description"
        const val AVATAR_EXTRA = "avatar"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CreateActivityScaffold(
                onClickBack = {
                    setResult(RESULT_CANCELED)
                    finish()
                },
                onClickCreate = { name: String, description: String, avatarUri: Uri? ->
                    val resultIntent = Intent()
                    resultIntent.putExtra(NAME_EXTRA, name)
                    resultIntent.putExtra(DESCRIPTION_EXTRA, description)
                    // Kotlin cannot use nullable types in Java interop generics
                    if (avatarUri != null) {
                        resultIntent.putExtra(AVATAR_EXTRA, avatarUri)
                    }
                    setResult(RESULT_OK, resultIntent)
                    finish()
                },
            )
        }
    }
}