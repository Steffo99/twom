package eu.steffo.twom.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import eu.steffo.twom.R
import eu.steffo.twom.matrix.LocalSession
import eu.steffo.twom.theme.TwoMTheme
import org.matrix.android.sdk.api.session.Session

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun MatrixActivityScaffold(
    onClickLogin: () -> Unit = {},
    onClickLogout: () -> Unit = {},
    onClickRoom: (roomId: String) -> Unit = {},
    session: Session? = null,
) {
    TwoMTheme {
        CompositionLocalProvider(LocalSession provides session) {
            Scaffold(
                topBar = {
                    CenterAlignedTopAppBar(
                        title = {
                            Text(LocalContext.current.getString(R.string.app_name))
                        },
                        actions = {
                            ProfileIconButton(
                                onClickLogout = onClickLogout
                            )
                        },
                    )
                }
            ) {
                if (session == null) {
                    MatrixActivityNotLoggedInControl(
                        modifier = Modifier.padding(it),
                        onClickLogin = onClickLogin,
                    )
                } else {
                    MainActivityRoomListControl(
                        modifier = Modifier.padding(it),
                        onClickRoom = onClickRoom,
                    )
                }
            }
        }
    }
}