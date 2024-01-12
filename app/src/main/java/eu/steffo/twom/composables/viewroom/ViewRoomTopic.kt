package eu.steffo.twom.composables.viewroom

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import eu.steffo.twom.R
import eu.steffo.twom.composables.errorhandling.ErrorText
import eu.steffo.twom.composables.theme.basePadding

@Composable
@Preview
fun ViewRoomTopic() {
    val roomSummaryRequest = LocalRoomSummary.current
    if (roomSummaryRequest == null) {
        ErrorText(stringResource(R.string.room_error_roomsummary_missing))
        return
    }

    val roomSummary = roomSummaryRequest.getOrNull()
    if (roomSummary == null) {
        ErrorText(stringResource(R.string.room_error_roomsummary_notfound))
        return
    }

    Row(Modifier.basePadding()) {
        Text(
            text = stringResource(R.string.room_topic_title),
            style = MaterialTheme.typography.labelLarge,
        )
    }

    Row(Modifier.basePadding()) {
        Text(roomSummary.topic)
    }
}