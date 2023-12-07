package eu.steffo.twom.room

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import eu.steffo.twom.matrix.LocalSession
import eu.steffo.twom.theme.TwoMTheme
import org.matrix.android.sdk.api.session.Session
import org.matrix.android.sdk.api.session.getRoomSummary

@Composable
fun RoomActivityScaffold(
    session: Session,
    roomId: String,
    onBack: () -> Unit = {},
) {
    val roomSummary = session.getRoomSummary(roomId)

    TwoMTheme {
        CompositionLocalProvider(LocalSession provides session) {
            CompositionLocalProvider(LocalRoom provides roomSummary) {
                Scaffold(
                    topBar = {
                        RoomActivityTopBar(
                            onBack = onBack,
                        )
                    },
                    content = {
                        RoomActivityContent(
                            modifier = Modifier.padding(it),
                        )
                    },
                )
            }
        }
    }
}