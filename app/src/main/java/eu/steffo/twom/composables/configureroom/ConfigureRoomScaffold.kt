package eu.steffo.twom.composables.configureroom

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import eu.steffo.twom.activities.ConfigureRoomActivity
import eu.steffo.twom.composables.matrix.LocalSession
import eu.steffo.twom.composables.theme.TwoMTheme
import eu.steffo.twom.utils.TwoMGlobals

@Composable
fun ConfigureRoomScaffold(
    initialConfiguration: ConfigureRoomActivity.Configuration? = null,
) {
    val session = TwoMGlobals.matrix.authenticationService().getLastAuthenticatedSession()!!

    TwoMTheme {
        CompositionLocalProvider(LocalSession provides session) {
            Scaffold(
                topBar = {
                    ConfigureActivityTopBar(
                        initialName = initialConfiguration?.name,
                    )
                },
                content = {
                    ConfigureRoomForm(
                        modifier = Modifier.padding(it),
                        initialConfiguration = initialConfiguration,
                    )
                }
            )
        }
    }
}