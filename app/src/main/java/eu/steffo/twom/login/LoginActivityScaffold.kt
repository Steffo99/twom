package eu.steffo.twom.login

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import eu.steffo.twom.theme.TwoMTheme
import org.matrix.android.sdk.api.session.Session


@Composable
@Preview
fun LoginActivityScaffold(
    onBack: () -> Unit = {},
    onLogin: (session: Session) -> Unit = {},
) {
    TwoMTheme {
        Scaffold(
            topBar = {
                LoginActivityTopBar(
                    onBack = onBack,
                )
            },
            content = {
                LoginActivityControl(
                    modifier = Modifier.padding(it),
                    onLogin = onLogin
                )
            },
        )
    }
}