package eu.steffo.twom.room

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import eu.steffo.twom.theme.TwoMPadding

@Composable
fun RoomActivityContent(
    modifier: Modifier = Modifier,
) {
    val isLoading = (LocalRoom.current == null)
    val roomSummary = LocalRoom.current?.getOrNull()
    val isError = (!isLoading && roomSummary == null)

    Column(modifier) {
        Row(TwoMPadding.base) {
            if (roomSummary != null) {
                Text(roomSummary.topic)
            }
        }
    }
}
