package eu.steffo.twom.login.components

import android.app.Activity
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import eu.steffo.twom.theme.components.TwoMTheme
import org.matrix.android.sdk.api.session.Session


@Composable
@Preview
fun LoginScaffold(
    onLogin: (session: Session) -> Unit = {},
) {
    val context = LocalContext.current
    val activity = context as Activity

    fun submitActivity() {
        activity.setResult(ComponentActivity.RESULT_OK, Intent())
        activity.finish()
    }

    TwoMTheme {
        Scaffold(
            topBar = {
                LoginActivityTopBar()
            },
            content = {
                LoginForm(
                    modifier = Modifier.padding(it),
                    onLogin = { submitActivity() }
                )
            },
        )
    }
}