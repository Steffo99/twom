package eu.steffo.twom.room

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import eu.steffo.twom.R
import eu.steffo.twom.matrix.LocalSession
import eu.steffo.twom.theme.TwoMPadding


@Composable
fun RoomActivityContent(
    modifier: Modifier = Modifier,
) {
    val isLoading = (LocalRoom.current == null)
    val roomSummary = LocalRoom.current?.getOrNull()
    val isError = (!isLoading && roomSummary == null)

    var rsvpAnswer by rememberSaveable { mutableStateOf<RSVPAnswer?>(null) }
    var rsvpComment by rememberSaveable { mutableStateOf("") }

    Column(modifier) {
        if (roomSummary != null) {
            Row(TwoMPadding.base) {
                Text(
                    text = stringResource(R.string.room_topic_title),
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Row(TwoMPadding.base) {
                Text(roomSummary.topic)
            }

            Row(TwoMPadding.base) {
                Text(
                    text = stringResource(R.string.room_rsvp_title),
                    style = MaterialTheme.typography.titleMedium
                )
            }

            RoomActivityChipSelector(
                value = rsvpAnswer,
                onChange = { rsvpAnswer = it }
            )

            if (rsvpAnswer != null) {
                Row(Modifier.padding(start = 10.dp, end = 10.dp, bottom = 10.dp)) {
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        value = rsvpComment,
                        onValueChange = { rsvpComment = it },
                        placeholder = {
                            Text(LocalContext.current.getString(R.string.room_rsvp_comment_placeholder))
                        },
                    )
                }
            }

            Row(TwoMPadding.base) {
                Text(
                    text = stringResource(R.string.room_invitees_title),
                    style = MaterialTheme.typography.titleMedium
                )
            }

            // TODO: Risky assertion?
            Column(TwoMPadding.base) {
                MemberListItem(
                    memberId = LocalSession.current!!.myUserId,
                    rsvpAnswer = rsvpAnswer,
                    rsvpComment = if (rsvpAnswer != null) {
                        rsvpComment
                    } else {
                        ""
                    },
                )

                roomSummary.otherMemberIds.forEach {
                    MemberListItem(
                        memberId = it,
                        rsvpAnswer = null,
                        rsvpComment = "",
                    )
                }
            }
        }
    }
}
