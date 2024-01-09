package eu.steffo.twom.room

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
@Preview
fun RoomActivityAnswerForm(
    currentRsvpAnswer: RSVPAnswer = RSVPAnswer.UNKNOWN,
    currentRsvpComment: String = "",
    onUpdate: (rsvpAnswer: RSVPAnswer, rsvpComment: String) -> Unit = { _, _ -> },
    isUpdating: Boolean = false,
) {
    var rsvpAnswer by rememberSaveable { mutableStateOf(currentRsvpAnswer) }
    var rsvpComment by rememberSaveable { mutableStateOf(currentRsvpComment) }

    val hasChanged = (rsvpAnswer != currentRsvpAnswer || rsvpComment != currentRsvpComment)

    RSVPAnswerSelectRow(
        value = rsvpAnswer,
        onChange = { rsvpAnswer = it },
    )
    RSVPCommentField(
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        value = rsvpComment,
        onChange = { rsvpComment = it },
        rsvpAnswer = rsvpAnswer,
    )
    RSVPUpdateButton(
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp, top = 4.dp, bottom = 4.dp)
            .fillMaxWidth(),
        onClick = { onUpdate(rsvpAnswer, rsvpComment) },
        enabled = hasChanged && !isUpdating,
        rsvpAnswer = rsvpAnswer,
    )
}
