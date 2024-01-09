package eu.steffo.twom.room

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import eu.steffo.twom.R
import eu.steffo.twom.matrix.LocalSession
import eu.steffo.twom.theme.ErrorText
import eu.steffo.twom.theme.TwoMPadding
import kotlinx.coroutines.launch
import org.matrix.android.sdk.api.failure.Failure
import kotlin.jvm.optionals.getOrNull


@Composable
fun RoomActivityContent(
    modifier: Modifier = Modifier,
) {
    val scope = rememberCoroutineScope()

    val session = LocalSession.current
    if (session == null) {
        ErrorText(stringResource(R.string.room_error_session_missing))
        return
    }

    val roomRequest = LocalRoom.current
    if (roomRequest == null) {
        ErrorText(stringResource(R.string.room_error_room_missing))
        return
    }

    val room = roomRequest.getOrNull()
    if (room == null) {
        ErrorText(stringResource(R.string.room_error_room_notfound))
        return
    }

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

    LaunchedEffect(roomSummary.otherMemberIds) ResolveUnknownUsers@{
        // Resolve unknown users, one at a time
        roomSummary.otherMemberIds.map {
            if (session.userService().getUser(it) == null) {
                Log.i("Room", "Resolving unknown user: $it")
                session.userService().resolveUser(it)
                Log.d("Room", "Successfully resolved unknown user: $it")
            } else {
                Log.v("Room", "Not resolving known user: $it")
            }
        }
    }

    val myRsvpRequest = observeRsvpAsLiveState(room = room, userId = session.myUserId)
    val otherRsvpRequests =
        roomSummary.otherMemberIds.map { it to observeRsvpAsLiveState(room = room, userId = it) }

    var isUpdatingMyRsvp by rememberSaveable { mutableStateOf(false) }
    var errorMyRsvp by rememberSaveable { mutableStateOf<Failure.ServerError?>(null) }

    Column(modifier) {
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
            // FIXME: This always set the request to UNKNOWN
            currentRsvpAnswer = myRsvpRequest?.second ?: RSVPAnswer.UNKNOWN,
            currentRsvpComment = myRsvpRequest?.third ?: "",
            onUpdate = { answer, comment ->
                isUpdatingMyRsvp = true
                errorMyRsvp = null

                scope.launch SendRSVP@{
                    Log.d(
                        "Room",
                        "Updating eu.steffo.twom.rsvp with answer `$answer` and comment `$comment`..."
                    )
                    try {
                        room.stateService().sendStateEvent(
                            eventType = "eu.steffo.twom.rsvp",
                            stateKey = session.myUserId,
                            body = mapOf(
                                pairs = arrayOf(
                                    "answer" to answer.toString(),
                                    "comment" to comment,
                                )
                            ),
                        )
                    } catch (error: Failure.ServerError) {
                        Log.e("Room", "Failed to update eu.steffo.twom.rsvp: $error")
                        errorMyRsvp = error
                        isUpdatingMyRsvp = false
                        return@SendRSVP
                    }
                    Log.d(
                        "Room",
                        "Updated eu.steffo.twom.rsvp with answer `$answer` and comment `$comment`!"
                    )

                    if (myRsvpRequest != null) {
                        val myRsvpRequestEventId = myRsvpRequest.first.eventId
                        Log.d(
                            "Room",
                            "Attempting to redact old eu.steffo.twom.rsvp event `${myRsvpRequestEventId}`..."
                        )
                        try {
                            room.sendService()
                                .redactEvent(myRsvpRequest.first, "Replaced with new information")
                        } catch (error: Failure.ServerError) {
                            Log.e("Room", "Failed to redact the old eu.steffo.twom.rsvp: $error")
                            errorMyRsvp = error
                            isUpdatingMyRsvp = false
                            return@SendRSVP
                        }
                    } else {
                        Log.d("Room", "Not doing anything else; there isn't anything to redact.")
                    }

                    isUpdatingMyRsvp = false
                }
            },
            isUpdating = isUpdatingMyRsvp,
        )

        if (errorMyRsvp != null) {
            // TODO: Maybe add an human-friendly error message?
            Row(TwoMPadding.base) {
                ErrorText(
                    errorMyRsvp.toString()
                )
            }
        }

        Row(TwoMPadding.base) {
            Text(
                text = stringResource(R.string.room_invitees_title),
                style = MaterialTheme.typography.labelLarge,
            )
        }

        Column(TwoMPadding.base) {
            MemberListItem(
                memberId = LocalSession.current!!.myUserId,
                rsvpAnswer = myRsvpRequest?.second ?: RSVPAnswer.UNKNOWN,
                rsvpComment = myRsvpRequest?.third ?: "",
            )

            otherRsvpRequests.forEach {
                MemberListItem(
                    memberId = it.first,
                    rsvpAnswer = it.second?.second ?: RSVPAnswer.UNKNOWN,
                    rsvpComment = it.second?.third ?: "",
                )
            }
        }
    }
}
