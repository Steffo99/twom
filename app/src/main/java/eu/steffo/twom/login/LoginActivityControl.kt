package eu.steffo.twom.login

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import eu.steffo.twom.R
import eu.steffo.twom.matrix.TwoMMatrix
import eu.steffo.twom.theme.TwoMPadding
import kotlinx.coroutines.launch
import org.matrix.android.sdk.api.session.Session


@Composable
@Preview(showBackground = true)
fun LoginActivityControl(
    modifier: Modifier = Modifier,
    onLogin: (session: Session) -> Unit = {},
) {
    val scope = rememberCoroutineScope()

    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    var loggingIn by rememberSaveable { mutableStateOf(false) }

    Column(modifier) {
        if (loggingIn) {
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth(),
            )
        } else {
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth(),
                progress = 0.0f,
            )
        }
        Row(TwoMPadding.base) {
            Text(LocalContext.current.getString(R.string.login_text))
        }
        Row(TwoMPadding.base) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = username,
                onValueChange = { username = it },
                label = {
                    Text(LocalContext.current.getString(R.string.login_username_label))
                },
                placeholder = {
                    Text(LocalContext.current.getString(R.string.login_username_placeholder))
                },
                supportingText = {
                    Text(LocalContext.current.getString(R.string.login_username_supporting))
                },
            )
        }
        Row(TwoMPadding.base) {
            PasswordField(
                modifier = Modifier.fillMaxWidth(),
                value = password,
                onValueChange = { password = it },
                label = {
                    Text(LocalContext.current.getString(R.string.login_password_label))
                },
                placeholder = {
                    Text(LocalContext.current.getString(R.string.login_password_placeholder))
                },
                supportingText = {
                    Text(LocalContext.current.getString(R.string.login_password_supporting))
                },
            )
        }
        Row(TwoMPadding.base) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    scope.launch Login@{
                        Log.d(this::class.qualifiedName, "Launching login wizard...")
                        val wizard = TwoMMatrix.matrix.authenticationService().getLoginWizard()

                        // TODO: Which exceptions can this spawn?
                        Log.d(this::class.qualifiedName, "Trying to login as: $username")
                        loggingIn = true

                        lateinit var session: Session
                        // TODO: Why does this not catch the exception?
                        try {
                            session = wizard.login(
                                login = username,
                                password = password,
                                initialDeviceName = "Garasauto", // TODO
                                deviceId = "Garasauto", // TODO
                            )
                        } catch (e: RuntimeException) {
                            Log.e(
                                this::class.qualifiedName,
                                "Something went wrong while logging in as: $username",
                                e
                            )
                            return@Login
                        } finally {
                            loggingIn = false
                        }

                        Log.d(
                            this::class.qualifiedName,
                            "Logged in with session id: ${session.sessionId}"
                        )
                        onLogin(session)
                    }
                },
                enabled = (username != ""),
            ) {
                Text(LocalContext.current.getString(R.string.login_complete_text))
            }
        }
    }
}