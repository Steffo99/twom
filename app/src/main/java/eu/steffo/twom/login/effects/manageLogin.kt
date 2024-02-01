package eu.steffo.twom.login.effects

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import eu.steffo.twom.R
import eu.steffo.twom.errorhandling.utils.LocalizableError
import eu.steffo.twom.errorhandling.utils.capture
import eu.steffo.twom.matrix.utils.TwoMMatrix
import org.matrix.android.sdk.api.auth.data.HomeServerConnectionConfig
import org.matrix.android.sdk.api.auth.wellknown.WellknownResult
import org.matrix.android.sdk.api.session.Session

private const val TAG = "manageLogin"

enum class LoginStep(val ord: Int) {
    NONE(0),
    RESET(1),
    WELLKNOWN(2),
    FLOWS(3),
    WIZARD(4),
    LOGIN(5),
    DONE(6),
}

data class LoginManager(
    val step: LoginStep,
    val error: LocalizableError?,
    val login: suspend (username: String, password: String) -> Session?,
)

@Composable
fun manageLogin(): LoginManager {
    var step by remember { mutableStateOf(LoginStep.NONE) }
    val error = remember { mutableStateOf<LocalizableError?>(null) }

    suspend fun login(username: String, password: String): Session? {
        Log.i(TAG, "Starting login process for: $username")
        step = LoginStep.NONE

        Log.d(TAG, "Resetting authentication service...")
        step = LoginStep.RESET
        val auth = TwoMMatrix.matrix.authenticationService()
        error.capture(R.string.login_error_wellknown_generic) {
            auth.reset()
        } ?: return null

        Log.d(TAG, "Retrieving .well-known data for: $username")
        step = LoginStep.WELLKNOWN
        lateinit var wellKnown: WellknownResult
        error.capture(R.string.login_error_wellknown_generic) {
            wellKnown = auth.getWellKnownData(
                matrixId = username,
                homeServerConnectionConfig = null,
            )
        } ?: return null
        if (wellKnown !is WellknownResult.Prompt) {
            error.value = LocalizableError(R.string.login_error_wellknown_missing)
            return null
        }

        Log.d(TAG, "Retrieving login flows for: $username")
        step = LoginStep.FLOWS
        error.capture(R.string.login_error_flows_generic) {
            auth.getLoginFlow(
                HomeServerConnectionConfig
                    .Builder()
                    .withHomeServerUri((wellKnown as WellknownResult.Prompt).homeServerUrl)
                    .build()
            )
        } ?: return null

        Log.d(TAG, "Getting login wizard...")
        step = LoginStep.WIZARD
        val wizard = auth.getLoginWizard()

        Log.d(TAG, "Logging in as: $username")
        step = LoginStep.LOGIN
        lateinit var session: Session
        error.capture(R.string.login_error_login_generic) {
            session = wizard.login(
                login = username,
                password = password,
                initialDeviceName = "TwoM (Android)"
            )
        } ?: return null

        Log.i(TAG, "Logged in as: $session")
        step = LoginStep.DONE
        return session
    }

    return LoginManager(
        step = step,
        error = error.value,
        login = ::login,
    )
}