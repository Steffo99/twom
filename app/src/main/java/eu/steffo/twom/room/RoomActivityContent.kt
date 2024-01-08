package eu.steffo.twom.room

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import eu.steffo.twom.R
import eu.steffo.twom.matrix.LocalSession
import eu.steffo.twom.theme.ErrorText
import eu.steffo.twom.theme.TwoMPadding
import kotlinx.coroutines.launch
import org.matrix.android.sdk.api.query.QueryStringValue
import kotlin.jvm.optionals.getOrNull


@Composable
fun RoomActivityContent(
    modifier: Modifier = Modifier,
) {
    val scope = rememberCoroutineScope()

    val session = LocalSession.current
    val roomRequest = LocalRoom.current
    val room = roomRequest?.getOrNull()
    val roomSummaryRequest = LocalRoomSummary.current
    val roomSummary = roomSummaryRequest?.getOrNull()

    val myRsvp = room?.stateService()?.getStateEventLive(
        eventType = "eu.steffo.twom.rsvp",
        stateKey = QueryStringValue.Equals(session!!.myUserId),
    )?.observeAsState()
    // TODO: I stopped here; how to retrieve the RSVP from this?

    Column(modifier) {
        if (session == null) {
            ErrorText(stringResource(R.string.room_error_session_missing))
        } else if (roomRequest == null) {
            ErrorText(stringResource(R.string.room_error_room_missing))
        } else if (!roomRequest.isPresent) {
            ErrorText(stringResource(R.string.room_error_room_notfound))
        } else if (roomSummaryRequest == null) {
            // Loading
        } else if (!roomSummaryRequest.hasValue()) {
            ErrorText(stringResource(R.string.room_error_room_notfound))
        } else if (roomSummary != null) {
            Row(TwoMPadding.base) {
                Text(
                    text = stringResource(R.string.room_topic_title),
                    style = MaterialTheme.typography.labelLarge,
                )
            }
            Row(TwoMPadding.base) {
                Text(roomSummary.topic)
            }

            Row(TwoMPadding.base) {
                Text(
                    text = stringResource(R.string.room_rsvp_title),
                    style = MaterialTheme.typography.labelLarge,
                )
            }

            RoomActivityAnswerForm(
                currentRsvpAnswer = myRsvp,
                currentRsvpComment = myRsvp,
                onUpdate = { answer, comment ->
                    scope.launch SendStateEvent@{
                        room!!.stateService().sendStateEvent(
                            eventType = "eu.steffo.twom.rsvp",
                            stateKey = session.myUserId,
                            body = mapOf(
                                pairs = arrayOf(
                                    "answer" to answer.toString(),
                                    "comment" to comment,
                                )
                            ),
                        )
                    }
                }
            )

            Row(TwoMPadding.base) {
                Text(
                    text = stringResource(R.string.room_invitees_title),
                    style = MaterialTheme.typography.labelLarge,
                )
            }

            Column(TwoMPadding.base) {
                MemberListItem(
                    memberId = LocalSession.current!!.myUserId,
                    rsvpAnswer = rsvpAnswer,
                    rsvpComment = rsvpComment,
                )

                roomSummary.otherMemberIds.forEach {
                    MemberListItem(
                        memberId = it,
                        rsvpAnswer = null,
                        rsvpComment = "",
                    )
                }
            }
        } else if (isError) {
            Row(TwoMPadding.base) {
                Text(
                    // TODO: Maybe add a better error string
                    text = stringResource(R.string.error),
                    color = MaterialTheme.colorScheme.error,
                )
            }
        }
    }
}
