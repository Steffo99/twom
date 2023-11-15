package eu.steffo.twom.ui.fragment

import android.net.Uri
import android.util.Log
import android.webkit.URLUtil
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import eu.steffo.twom.R
import eu.steffo.twom.ui.input.HomeserverField
import eu.steffo.twom.ui.input.HomeserverFieldState
import eu.steffo.twom.ui.scaffold.LocalMatrix
import eu.steffo.twom.ui.scaffold.TwoMMatrixProvider
import eu.steffo.twom.ui.scaffold.TwoMTopAppBar
import eu.steffo.twom.ui.theme.TwoMTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.matrix.android.sdk.api.auth.data.HomeServerConnectionConfig

@Composable
fun LoginFragment() {
    TwoMMatrixProvider {
        TwoMTheme {
            LoginContents()
        }
    }
}

@Composable
@Preview
fun LoginContents() {
    val scope = rememberCoroutineScope()
    val matrix = LocalMatrix.current

    var homeserver by rememberSaveable { mutableStateOf("") }
    var homeserverFieldState by rememberSaveable { mutableStateOf<HomeserverFieldState>(HomeserverFieldState.Empty) }
    var homeserverUrlValid by rememberSaveable { mutableStateOf<Boolean?>(null) }
    var homeserverFlowValid by rememberSaveable { mutableStateOf<Boolean?>(null) }

    TwoMTopAppBar {
        Column(
            modifier = Modifier.padding(it)
        ) {
            Row(
                modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 10.dp)
            ) {
                Text(LocalContext.current.getString(R.string.login_welcome_text))
            }
            Row(
                modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 10.dp)
            ) {
                HomeserverField(
                    modifier = Modifier.fillMaxWidth(),
                    value = homeserver,
                    onValueChange = OnValueChange@{
                        homeserver = it
                        homeserverUrlValid = null
                        homeserverFlowValid = null
                        homeserverFieldState = HomeserverFieldState.Empty

                        if(homeserver == "") {
                            return@OnValueChange
                        }

                        if(!(URLUtil.isHttpUrl(homeserver) || URLUtil.isHttpsUrl(homeserver))) {
                            homeserverUrlValid = false
                            return@OnValueChange
                        }
                        homeserverUrlValid = true

                        val uri = Uri.parse(homeserver)

                        scope.launch ValidateFlows@{
                            homeserverFieldState = HomeserverFieldState.Waiting
                            delay(500L)
                            if(homeserver != it) return@ValidateFlows

                            val authenticationService = matrix!!.authenticationService()

                            homeserverFieldState = HomeserverFieldState.Validating
                            try {
                                authenticationService.getLoginFlow(HomeServerConnectionConfig(
                                    homeServerUri = uri,
                                ))
                            }
                            catch(e: Throwable) {
                                Log.e("LoginFragment", "Failed to get flows for homeserver", e)
                                homeserverFieldState = HomeserverFieldState.Done
                                homeserverFlowValid = false
                                return@ValidateFlows
                            }

                            homeserverFieldState = HomeserverFieldState.Done
                            homeserverFlowValid = true
                        }
                    },
                    state = homeserverFieldState,
                    error =
                        if(homeserverUrlValid == false) LocalContext.current.getString(R.string.homeserver_error_malformedurl)
                        else if(homeserverFlowValid == false) LocalContext.current.getString(R.string.homeserver_error_notmatrix)
                        else null
                    ,
                    enabled = (LocalMatrix.current != null),
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 10.dp)
            ) {
                Button(
                    onClick = {},
                    enabled = homeserverUrlValid == true && homeserverFlowValid == true,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(LocalContext.current.getString(R.string.login_button_continue_text))
                }
            }
        }
    }
}