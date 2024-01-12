package eu.steffo.twom.composables.viewroom

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import eu.steffo.twom.composables.matrix.LocalSession
import eu.steffo.twom.composables.theme.TwoMTheme
import org.matrix.android.sdk.api.session.Session
import java.util.Optional

@Composable
fun ViewRoomScaffold(
    session: Session,
    roomId: String,
) {
    val room = Optional.ofNullable(session.roomService().getRoom(roomId))
    val roomSummary by session.roomService().getRoomSummaryLive(roomId).observeAsState()

    TwoMTheme {
        CompositionLocalProvider(LocalSession provides session) {
            CompositionLocalProvider(LocalRoom provides room) {
                CompositionLocalProvider(LocalRoomSummary provides roomSummary) {
                    Scaffold(
                        topBar = {
                            ViewRoomTopBar()
                        },
                        content = {
                            ViewRoomContent(
                                modifier = Modifier.padding(it),
                            )
                        },
                    )
                }
            }
        }
    }
}