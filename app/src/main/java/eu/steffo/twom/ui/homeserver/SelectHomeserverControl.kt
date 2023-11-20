package eu.steffo.twom.ui.homeserver

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
import eu.steffo.twom.matrix.TwoMMatrix
import eu.steffo.twom.ui.BASE_PADDING
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.matrix.android.sdk.api.auth.data.HomeServerConnectionConfig


@Composable
@Preview(showBackground = true)
fun SelectHomeserverControl(
    modifier: Modifier = Modifier,
    onComplete: (homeserver: String) -> Unit = {},
) {
    val scope = rememberCoroutineScope()

    var homeserver by rememberSaveable { mutableStateOf("") }
    var state by rememberSaveable { mutableStateOf(SelectHomeserverFieldState.Empty) }

    Column(modifier) {
        Row(BASE_PADDING) {
            Text(LocalContext.current.getString(R.string.selecthomeserver_text))
        }
        Row(BASE_PADDING) {
            SelectHomeserverField(
                modifier = Modifier.fillMaxWidth(),
                value = homeserver,
                onValueChange = OnValueChange@{
                    homeserver = it
                    state = SelectHomeserverFieldState.Empty

                    if (homeserver == "") {
                        return@OnValueChange
                    }

                    if (!(URLUtil.isHttpUrl(homeserver) || URLUtil.isHttpsUrl(homeserver))) {
                        state = SelectHomeserverFieldState.URLInvalid
                        return@OnValueChange
                    }

                    val uri = Uri.parse(homeserver)

                    scope.launch ValidateFlows@{
                        state = SelectHomeserverFieldState.Waiting
                        delay(500L)
                        if (homeserver != it) return@ValidateFlows

                        val authenticationService = TwoMMatrix.matrix!!.authenticationService()

                        state = SelectHomeserverFieldState.Validating
                        try {
                            authenticationService.getLoginFlow(
                                HomeServerConnectionConfig(
                                    homeServerUri = uri,
                                )
                            )
                        } catch (e: Throwable) {
                            state = SelectHomeserverFieldState.FlowInvalid
                            return@ValidateFlows
                        }

                        state = SelectHomeserverFieldState.Valid
                    }
                },
                enabled = (TwoMMatrix.matrix != null),
                state = state,
            )
        }
        Row(BASE_PADDING) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onComplete(homeserver)
                },
                enabled = (state == SelectHomeserverFieldState.Valid),
            ) {
                Text(LocalContext.current.getString(R.string.selecthomeserver_complete_text))
            }
        }
    }
}