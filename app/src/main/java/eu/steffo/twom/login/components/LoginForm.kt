package eu.steffo.twom.login.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import eu.steffo.twom.R
import eu.steffo.twom.errorhandling.components.ErrorText
import eu.steffo.twom.errorhandling.utils.Display
import eu.steffo.twom.login.effects.LoginStep
import eu.steffo.twom.login.effects.manageLogin
import eu.steffo.twom.theme.utils.basePadding
import kotlinx.coroutines.launch
import org.matrix.android.sdk.api.session.Session


@Composable
@Preview(showBackground = true)
fun LoginForm(
    modifier: Modifier = Modifier,
    onLogin: (session: Session) -> Unit = {},
) {
    val scope = rememberCoroutineScope()

    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    val manager = manageLogin()

    Column(modifier) {
        LinearProgressIndicator(
            modifier = Modifier.fillMaxWidth(),
            progress = manager.step.ord.toFloat() / LoginStep.DONE.ord.toFloat(),
            color = if (manager.error != null) {
                MaterialTheme.colorScheme.error
            } else {
                ProgressIndicatorDefaults.linearColor
            }
        )
        Row(Modifier.basePadding()) {
            Text(stringResource(R.string.login_intro_text))
        }
        Row(Modifier.basePadding()) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                value = username,
                onValueChange = { username = it },
                label = {
                    Text(stringResource(R.string.login_username_label))
                },
                placeholder = {
                    Text(stringResource(R.string.login_username_placeholder))
                },
                supportingText = {
                    Text(stringResource(R.string.login_username_supporting))
                },
            )
        }
        Row(Modifier.basePadding()) {
            PasswordField(
                modifier = Modifier.fillMaxWidth(),
                value = password,
                onValueChange = { password = it },
                label = {
                    Text(stringResource(R.string.login_password_label))
                },
                placeholder = {
                    Text(stringResource(R.string.login_password_placeholder))
                },
                supportingText = {
                    Text(stringResource(R.string.login_password_supporting))
                },
            )
        }
        Row(Modifier.basePadding()) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                enabled = (username != "" && (manager.step == LoginStep.NONE || manager.error != null)),
                onClick = {
                    scope.launch DoLogin@{
                        val session = manager.login(username, password) ?: return@DoLogin
                        onLogin(session)
                    }
                },
            ) {
                Text(stringResource(R.string.login_submit_text))
            }
        }
        manager.error.Display {
            Row(Modifier.basePadding()) {
                ErrorText(
                    text = it
                )
            }
        }
    }
}