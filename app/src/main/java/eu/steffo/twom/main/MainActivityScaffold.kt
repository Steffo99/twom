package eu.steffo.twom.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import eu.steffo.twom.matrix.LocalSession
import eu.steffo.twom.theme.TwoMTheme
import org.matrix.android.sdk.api.session.Session

@Composable
@Preview
fun MatrixActivityScaffold(
    onClickLogin: () -> Unit = {},
    onClickLogout: () -> Unit = {},
    onClickRoom: (roomId: String) -> Unit = {},
    onClickCreate: () -> Unit = {},
    session: Session? = null,
) {
    TwoMTheme {
        CompositionLocalProvider(LocalSession provides session) {
            Scaffold(
                topBar = {
                    MainActivityTopBar(
                        onClickLogin = onClickLogin,
                        onClickLogout = onClickLogout,
                    )
                },
                floatingActionButton = {
                    MainActivityCreateFAB(
                        onClickCreate = onClickCreate,
                    )
                },
                content = {
                    MainActivityContent(
                        modifier = Modifier.padding(it),
                        onClickRoom = onClickRoom,
                    )
                }
            )
        }
    }
}