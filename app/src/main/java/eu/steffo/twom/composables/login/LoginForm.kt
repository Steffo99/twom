package eu.steffo.twom.composables.login

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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import eu.steffo.twom.R
import eu.steffo.twom.composables.errorhandling.ErrorText
import eu.steffo.twom.composables.errorhandling.LocalizableError
import eu.steffo.twom.composables.fields.PasswordField
import eu.steffo.twom.composables.theme.basePadding
import eu.steffo.twom.utils.TwoMGlobals
import kotlinx.coroutines.launch
import org.matrix.android.sdk.api.auth.data.HomeServerConnectionConfig
import org.matrix.android.sdk.api.auth.data.LoginFlowResult
import org.matrix.android.sdk.api.auth.login.LoginWizard
import org.matrix.android.sdk.api.auth.wellknown.WellknownResult
import org.matrix.android.sdk.api.failure.MatrixIdFailure
import org.matrix.android.sdk.api.session.Session


enum class LoginStep(val step: Int) {
    NONE(0),
    SERVICE(1),
    WELLKNOWN(2),
    FLOWS(3),
    WIZARD(4),
    LOGIN(5),
    DONE(6),
}


// TODO: Localize error messages
@Composable
@Preview(showBackground = true)
fun LoginForm(
    modifier: Modifier = Modifier,
    onLogin: (session: Session) -> Unit = {},
) {
    val scope = rememberCoroutineScope()

    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    var loginStep by rememberSaveable { mutableStateOf(LoginStep.NONE) }
    val error by remember { mutableStateOf(LocalizableError()) }

    suspend fun doLogin() {
        error.clear()

        Log.d("Login", "Getting authentication service...")
        loginStep = LoginStep.SERVICE
        val auth = TwoMGlobals.matrix.authenticationService()

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
            error.set(R.string.login_error_username_invalid)
            return
        } catch (e: Throwable) {
            Log.e(
                "Login",
                "Something went wrong while retrieving .well-known data for: $username",
                e
            )
            error.set(R.string.login_error_wellknown_generic, e)
            return
        }
        if (wellKnown !is WellknownResult.Prompt) {
            Log.w(
                "Login",
                "Data is not .well-known for: $username"
            )
            error.set(R.string.login_error_wellknown_missing)
            return
        }

        Log.d("Login", "Retrieving login flows for: ${wellKnown.homeServerUrl}")
        loginStep = LoginStep.FLOWS
        @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
        lateinit var flows: LoginFlowResult
        try {
            @Suppress("UNUSED_VALUE")
            flows = auth.getLoginFlow(
                HomeServerConnectionConfig
                    .Builder()
                    .withHomeServerUri(wellKnown.homeServerUrl)
                    .build()
            )
        } catch (e: Throwable) {
            Log.e(
                "Login",
                "Something went wrong while retrieving login flows for: ${wellKnown.homeServerUrl}",
                e
            )
            error.set(R.string.login_error_flows_generic, e)
            return
        }

        Log.d("Login", "Creating login wizard...")
        loginStep = LoginStep.WIZARD
        lateinit var wizard: LoginWizard
        try {
            wizard = auth.getLoginWizard()  // Why is this stateful? Aargh.
        } catch (e: Throwable) {
            Log.e(
                "Login",
                "Something went wrong while setting up the login wizard.",
                e
            )
            error.set(R.string.login_error_wizard_generic, e)
            return
        }

        Log.d("Login", "Logging in as: $username")
        loginStep = LoginStep.LOGIN
        lateinit var session: Session
        try {
            session = wizard.login(
                login = username,
                password = password,
                initialDeviceName = "TwoM (Android)",
            )
        } catch (e: Throwable) {
            Log.e(
                "Login",
                "Something went wrong while logging in as: $username",
                e
            )
            error.set(R.string.login_error_login_generic, e)
            return
        }

        Log.d(
            "Login",
            "Logged in successfully with session id: ${session.sessionId}"
        )
        loginStep = LoginStep.DONE

        onLogin(session)
    }

    Column(modifier) {
        LinearProgressIndicator(
            modifier = Modifier.fillMaxWidth(),
            progress = loginStep.step.toFloat() / LoginStep.DONE.step.toFloat(),
            color = if (error.occurred()) MaterialTheme.colorScheme.error else ProgressIndicatorDefaults.linearColor
        )
        Row(Modifier.basePadding()) {
            Text(LocalContext.current.getString(R.string.login_text))
        }
        Row(Modifier.basePadding()) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
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
        Row(Modifier.basePadding()) {
            PasswordField(
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
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
        Row(Modifier.basePadding()) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                enabled = (username != "" && (loginStep == LoginStep.NONE || error.occurred())),
                onClick = {
                    scope.launch { doLogin() }
                },
            ) {
                Text(LocalContext.current.getString(R.string.login_complete_text))
            }
        }
        error.Show {
            Row(Modifier.basePadding()) {
                ErrorText(
                    text = it
                )
            }
        }
    }
}