package eu.steffo.twom.composables.viewroom

import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import eu.steffo.twom.R
import eu.steffo.twom.composables.errorhandling.Display
import eu.steffo.twom.composables.errorhandling.ErrorText
import eu.steffo.twom.composables.errorhandling.LoadingText
import eu.steffo.twom.composables.errorhandling.LocalizableError
import eu.steffo.twom.composables.matrix.LocalSession
import eu.steffo.twom.composables.theme.basePadding
import kotlinx.coroutines.launch
import org.matrix.android.sdk.api.session.room.model.Membership
import kotlin.jvm.optionals.getOrNull

@Composable
fun ViewRoomForm() {
    Row(Modifier.basePadding()) {
        Text(
            text = stringResource(R.string.room_rsvp_title),
            style = MaterialTheme.typography.labelLarge,
        )
    }

    val scope = rememberCoroutineScope()

    val session = LocalSession.current
    if (session == null) {
        Row(Modifier.basePadding()) {
            ErrorText(
                text = stringResource(R.string.error_session_missing)
            )
        }
        return
    }

    val roomRequest = LocalRoom.current
    if (roomRequest == null) {
        Row(Modifier.basePadding()) {
            LoadingText()
        }
        return
    }

    val room = roomRequest.getOrNull()
    if (room == null) {
        Row(Modifier.basePadding()) {
            ErrorText(
                text = stringResource(R.string.room_error_room_notfound)
            )
        }
        return
    }

    val member = room.membershipService().getRoomMember(session.myUserId)
    if (member == null || member.membership != Membership.JOIN) {
        Row(Modifier.basePadding()) {
            ErrorText(
                text = stringResource(R.string.room_error_self_notfound)
            )
        }
        return
    }

    // JOIN status is checked above
    val published = observeRSVP(room = room, member = member)!!

    var isPublishRunning by rememberSaveable { mutableStateOf(false) }
    var publishError by remember { mutableStateOf<LocalizableError?>(null) }

    RSVPForm(
        published = published,
        onRequestPublish = { answer, comment ->
            isPublishRunning = true
            publishError = null

            scope.launch Publish@{
                Log.d(
                    "ViewRoomForm",
                    "Updating RSVP with answer `$answer` and comment `$comment`..."
                )
                try {
                    room.stateService().sendStateEvent(
                        eventType = "eu.steffo.twom.rsvp",
                        stateKey = session.myUserId,
                        body = mapOf(
                            "answer" to answer.value,
                            "comment" to comment,
                        ),
                    )
                } catch (e: Throwable) {
                    Log.e("Room", "Failed to update eu.steffo.twom.rsvp: $publishError")
                    publishError = LocalizableError(R.string.room_error_publish_generic, e)
                    isPublishRunning = false
                    return@Publish
                }
                Log.d(
                    "ViewRoomForm",
                    "Updated RSVP with answer `$answer` and comment `$comment`!"
                )

                if (published.event != null) {
                    Log.d(
                        "Room",
                        "Attempting to redact old RSVP `${published.event.eventId}`..."
                    )
                    try {
                        room.sendService()
                            .redactEvent(published.event, "Replaced with new information")
                    } catch (e: Throwable) {
                        Log.e(
                            "Room",
                            "Failed to redact old RSVP: $publishError"
                        )
                        publishError = LocalizableError(R.string.room_error_redact_generic, e)
                        isPublishRunning = false
                        return@Publish
                    }
                } else {
                    Log.d(
                        "Room",
                        "Not doing anything else; there isn't anything to redact."
                    )
                }

                isPublishRunning = false
            }
        },
        isPublishRunning = isPublishRunning,
    )

    publishError.Display {
        Row(Modifier.basePadding()) {
            ErrorText(
                text = it
            )
        }
    }
}