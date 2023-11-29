package eu.steffo.twom.login

import android.util.Log
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import eu.steffo.twom.R
import eu.steffo.twom.matrix.TwoMMatrix
import eu.steffo.twom.theme.TwoMPadding
import kotlinx.coroutines.launch
import org.matrix.android.sdk.api.auth.data.HomeServerConnectionConfig
import org.matrix.android.sdk.api.auth.data.LoginFlowResult
import org.matrix.android.sdk.api.auth.login.LoginWizard
import org.matrix.android.sdk.api.auth.wellknown.WellknownResult
import org.matrix.android.sdk.api.failure.MatrixIdFailure
import org.matrix.android.sdk.api.session.Session


// TODO: Localize error messages
@Composable
@Preview(showBackground = true)
fun LoginActivityControl(
    modifier: Modifier = Modifier,
    onLogin: (session: Session) -> Unit = {},
) {
    val scope = rememberCoroutineScope()

    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    var loginStep by rememberSaveable { mutableStateOf(LoginStep.NONE) }
    var errorMessage by rememberSaveable { mutableStateOf<String?>(null) }

    Column(modifier) {
        LinearProgressIndicator(
            modifier = Modifier.fillMaxWidth(),
            progress = loginStep.step.toFloat() / LoginStep.DONE.step.toFloat(),
            color = if (errorMessage != null) MaterialTheme.colorScheme.error else ProgressIndicatorDefaults.linearColor
        )
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
                        errorMessage = null

                        Log.d("Login", "Getting authentication service...")
                        loginStep = LoginStep.SERVICE
                        val auth = TwoMMatrix.matrix.authenticationService()

                        Log.d("Login", "Resetting authentication service...")
                        auth.reset()

                        Log.d("Login", "Retrieving .well-known data for: $username")
                        loginStep = LoginStep.WELLKNOWN
                        lateinit var wellKnown: WellknownResult
                        try {
                            wellKnown = auth.getWellKnownData(username, null)
                        } catch (e: MatrixIdFailure.InvalidMatrixId) {
                            Log.d(
                                "Login",
                                "User seems to have input an invalid Matrix ID: $username",
                                e
                            )
                            errorMessage = "The input Matrix ID is invalid."
                            return@Login
                        } catch (e: Throwable) {
                            // TODO: It sure would be nice to know which exceptions can be thrown here.
                            Log.e(
                                "Login",
                                "Something went wrong while retrieving .well-known data for: $username",
                                e
                            )
                            errorMessage = e.message
                            return@Login
                        }
                        if (wellKnown !is WellknownResult.Prompt) {
                            Log.w(
                                "Login",
                                "Data is not .well-known for: $username"
                            )
                            errorMessage =
                                "Non .well-known Matrix servers are not currently supported by TwoM."
                            return@Login
                        }

                        Log.d("Login", "Retrieving login flows for: ${wellKnown.homeServerUrl}")
                        loginStep = LoginStep.FLOWS
                        lateinit var flows: LoginFlowResult
                        try {
                            flows = auth.getLoginFlow(
                                HomeServerConnectionConfig
                                    .Builder()
                                    .withHomeServerUri(wellKnown.homeServerUrl)
                                    .build()
                            )
                        } catch (e: Throwable) {
                            // TODO: It sure would be nice to know which exceptions can be thrown here.
                            Log.e(
                                "Login",
                                "Something went wrong while retrieving login flows for: ${wellKnown.homeServerUrl}",
                                e
                            )
                            errorMessage = e.message
                            return@Login
                        }

                        Log.d("Login", "Creating login wizard...")
                        loginStep = LoginStep.WIZARD
                        lateinit var wizard: LoginWizard
                        try {
                            wizard = auth.getLoginWizard()  // Why is this stateful? Aargh.
                        } catch (e: Throwable) {
                            // TODO: It sure would be nice to know which exceptions can be thrown here.
                            Log.e(
                                "Login",
                                "Something went wrong while creating the login wizard.",
                                e
                            )
                            errorMessage = e.message
                            return@Login
                        }

                        Log.d("Login", "Logging in as: $username")
                        loginStep = LoginStep.LOGIN
                        lateinit var session: Session
                        try {
                            session = wizard.login(
                                login = username,
                                password = password,
                                initialDeviceName = "Garasauto",  // TODO: Set a proper device name
                                deviceId = "Garasauto",  // TODO: Set a proper device id
                            )
                        } catch (e: Throwable) {
                            Log.e(
                                "Login",
                                "Something went wrong while logging in as: $username",
                                e
                            )
                            errorMessage = e.message
                            return@Login
                        }

                        Log.d(
                            "Login",
                            "Logged in successfully with session id: ${session.sessionId}"
                        )
                        loginStep = LoginStep.DONE

                        onLogin(session)
                    }
                },
                enabled = (username != "" && (loginStep == LoginStep.NONE || errorMessage != null)),
            ) {
                Text(LocalContext.current.getString(R.string.login_complete_text))
            }
        }
        if (errorMessage != null) {
            Row(TwoMPadding.base) {
                ErrorText(text = errorMessage ?: "Unknown error")
            }
        }
    }
}