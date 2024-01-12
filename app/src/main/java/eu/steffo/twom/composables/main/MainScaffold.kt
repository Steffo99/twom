package eu.steffo.twom.composables.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import eu.steffo.twom.composables.matrix.LocalSession
import eu.steffo.twom.composables.theme.TwoMTheme
import org.matrix.android.sdk.api.session.Session

@Composable
@Preview
fun MainScaffold(
    processLogin: () -> Unit = {},
    processLogout: () -> Unit = {},
    processCreate: (name: String, description: String, avatarUri: String?) -> Unit = { _, _, _ -> },
    session: Session? = null,
) {
    TwoMTheme {
        CompositionLocalProvider(LocalSession provides session) {
            Scaffold(
                topBar = {
                    MainTopBar(
                        processLogin = processLogin,
                        processLogout = processLogout,
                    )
                },
                floatingActionButton = {
                    CreateRoomFAB(
                        onCreateParamsSelected = processCreate,
                    )
                },
                content = {
                    if (session == null) {
                        MainContentNotLoggedIn(Modifier.padding(it))
                    } else {
                        MainContentLoggedIn(Modifier.padding(it))
                    }
                }
            )
        }
    }
}