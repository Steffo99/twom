package eu.steffo.twom.composables.configureroom

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import eu.steffo.twom.activities.ConfigureRoomActivity
import eu.steffo.twom.composables.theme.TwoMTheme

@Composable
@Preview
fun ConfigureRoomScaffold(
    initialConfiguration: ConfigureRoomActivity.Configuration? = null,
) {
    TwoMTheme {
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