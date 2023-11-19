package eu.steffo.twom.ui.fragment

import android.net.Uri
import android.webkit.URLUtil
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import eu.steffo.twom.global.TwoMMatrix
import eu.steffo.twom.ui.input.SelectHomeserverField
import eu.steffo.twom.ui.input.SelectHomeserverFieldState
import eu.steffo.twom.ui.scaffold.TwoMTopAppBar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.matrix.android.sdk.api.auth.data.HomeServerConnectionConfig

@Composable
@Preview
fun HomeserverFragment() {
    val scope = rememberCoroutineScope()
    val matrix = TwoMMatrix.matrix

    var homeserver by rememberSaveable { mutableStateOf("") }
    var state by rememberSaveable { mutableStateOf(SelectHomeserverFieldState.Empty) }
    var urlValid by rememberSaveable { mutableStateOf<Boolean?>(null) }
    var flowValid by rememberSaveable { mutableStateOf<Boolean?>(null) }

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
                SelectHomeserverField(
                    modifier = Modifier.fillMaxWidth(),
                    value = homeserver,
                    onValueChange = OnValueChange@{
                        homeserver = it
                        urlValid = null
                        flowValid = null
                        state = SelectHomeserverFieldState.Empty

                        if(homeserver == "") {
                            return@OnValueChange
                        }

                        if(!(URLUtil.isHttpUrl(homeserver) || URLUtil.isHttpsUrl(homeserver))) {
                            state = SelectHomeserverFieldState.Error
                            urlValid = false
                            return@OnValueChange
                        }
                        urlValid = true

                        val uri = Uri.parse(homeserver)

                        scope.launch ValidateFlows@{
                            state = SelectHomeserverFieldState.Waiting
                            delay(500L)
                            if(homeserver != it) return@ValidateFlows

                            val authenticationService = matrix.authenticationService()

                            state = SelectHomeserverFieldState.Validating
                            try {
                                authenticationService.getLoginFlow(HomeServerConnectionConfig(
                                    homeServerUri = uri,
                                ))
                            }
                            catch(e: Throwable) {
                                state = SelectHomeserverFieldState.Error
                                flowValid = false
                                return@ValidateFlows
                            }

                            state = SelectHomeserverFieldState.Done
                            flowValid = true
                        }
                    },
                    state = state,
                    error =
                        if(urlValid == false) LocalContext.current.getString(R.string.homeserver_error_malformedurl)
                        else if(flowValid == false) LocalContext.current.getString(R.string.homeserver_error_notmatrix)
                        else null
                    ,
                )
            }
            Row(
                modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 10.dp)
            ) {
                Button(
                    onClick = {},
                    enabled = urlValid == true && flowValid == true,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(LocalContext.current.getString(R.string.login_button_continue_text))
                }
            }
        }
    }
}