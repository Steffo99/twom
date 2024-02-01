package eu.steffo.twom.configureroom.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import eu.steffo.twom.configureroom.activities.ConfigureRoomActivity
import eu.steffo.twom.matrix.complocals.LocalSession
import eu.steffo.twom.matrix.utils.TwoMMatrix
import eu.steffo.twom.theme.components.TwoMTheme

@Composable
fun ConfigureRoomScaffold(
    initialConfiguration: ConfigureRoomActivity.Configuration? = null,
) {
    val session = TwoMMatrix.matrix.authenticationService().getLastAuthenticatedSession()!!

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