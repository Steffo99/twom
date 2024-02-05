package eu.steffo.twom.viewroom.components

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import eu.steffo.twom.R
import eu.steffo.twom.avatar.components.AvatarUser
import eu.steffo.twom.errorhandling.components.ErrorText
import eu.steffo.twom.matrix.complocals.LocalSession
import eu.steffo.twom.viewroom.complocals.LocalRoom
import eu.steffo.twom.viewroom.effects.canCurrentSessionKickHere
import eu.steffo.twom.viewroom.effects.observeRSVP
import eu.steffo.twom.viewroom.effects.resolveUser
import eu.steffo.twom.viewroom.utils.RSVPAnswer
import kotlinx.coroutines.launch
import org.matrix.android.sdk.api.session.room.model.RoomMemberSummary
import kotlin.jvm.optionals.getOrNull

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MemberListItem(
    member: RoomMemberSummary,
    modifier: Modifier = Modifier,
) {
    val session = LocalSession.current
    if (session == null) {
        ErrorText(
            text = stringResource(R.string.error_session_missing)
        )
        return
    }

    val roomRequest = LocalRoom.current
    if (roomRequest == null) {
        ErrorText(
            text = stringResource(R.string.room_error_room_missing)
        )
        return
    }

    val room = roomRequest.getOrNull()
    if (room == null) {
        ErrorText(
            text = stringResource(R.string.invite_error_room_notfound)
        )
        return
    }

    // This might not be necessary; I'm not sure when the internal Matrix client resolves users
    val user = resolveUser(member.userId)
    val canKick = canCurrentSessionKickHere()

    val scope = rememberCoroutineScope()

    val rsvp = observeRSVP(room = room, member = member) ?: return

    var running by rememberSaveable { mutableStateOf(false) }
    var expanded by rememberSaveable { mutableStateOf(false) }

    val alpha = if (rsvp.answer == RSVPAnswer.PENDING) 0.4f else 1.0f
    val color = rsvp.answer.staticColorRole.color().copy(alpha)

    // A parent layout is needed for the Dropdown to display in the correct position
    Box {

        ListItem(
            modifier = modifier
                .combinedClickable(
                    onClick = {},
                    onLongClick = { expanded = true },
                ),
            headlineContent = {
                Text(
                    text = user?.displayName ?: stringResource(R.string.user_unresolved_name),
                    color = color,
                    style = MaterialTheme.typography.titleMedium,
                )
            },
            leadingContent = {
                Box(
                    Modifier
                        .padding(end = 10.dp)
                        .size(40.dp)
                        .clip(MaterialTheme.shapes.extraLarge)
                ) {
                    if (running) {
                        CircularProgressIndicator()
                    } else {
                        AvatarUser(
                            user = user,
                            alpha = alpha,
                        )
                    }
                }
            },
            trailingContent = {
                Icon(
                    imageVector = rsvp.answer.icon,
                    contentDescription = rsvp.answer.toResponse(),
                    tint = color,
                )
            },
            supportingContent = {
                if (rsvp.comment != "") {
                    Text(
                        text = rsvp.comment,
                        color = color,
                    )
                }
            },
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            if (member.userId != session.myUserId && canKick) {
                DropdownMenuItem(
                    text = {
                        Text(stringResource(R.string.room_uninvite_label))
                    },
                    onClick = {
                        expanded = false

                        scope.launch SendUninvite@{
                            val userId = member.userId

                            Log.d("Room", "Uninviting `$userId`...")
                            running = true

                            try {
                                room.membershipService().remove(userId)
                            } catch (e: Throwable) {
                                Log.e("Room", "Failed to uninvite `$userId`: $e")
                                return@SendUninvite
                            } finally {
                                running = false
                            }

                            Log.d("Room", "Successfully uninvited `$userId`!")
                        }
                    },
                )
            }
        }
    }
}
