package eu.steffo.twom.composables.viewroom

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import eu.steffo.twom.R
import eu.steffo.twom.composables.avatar.components.AvatarUser
import eu.steffo.twom.composables.errorhandling.ErrorText
import eu.steffo.twom.composables.matrix.LocalSession
import eu.steffo.twom.utils.RSVPAnswer
import kotlinx.coroutines.launch
import org.matrix.android.sdk.api.session.room.model.RoomMemberSummary
import org.matrix.android.sdk.api.session.user.model.User
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
            text = stringResource(R.string.room_error_room_notfound)
        )
        return
    }

    // TODO: Is this necessary?

    var user by remember { mutableStateOf<User?>(null) }

    LaunchedEffect(session, member.userId) {
        val memberId = member.userId
        Log.d("UserListItem", "Resolving user: $memberId")
        user = session.userService().resolveUser(memberId)
        Log.d("UserListItem", "Resolved user: $memberId")
    }

    val scope = rememberCoroutineScope()

    val rsvp = observeRSVP(room = room, member = member) ?: return

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
                    AvatarUser(
                        user = user,
                        alpha = alpha,
                    )
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
            // TODO: Also hide if unprivileged
            if (member.userId != session.myUserId) {
                DropdownMenuItem(
                    text = {
                        Text(stringResource(R.string.room_uninvite_label))
                    },
                    onClick = {
                        expanded = false

                        scope.launch SendUninvite@{
                            val userId = member.userId

                            Log.d("Room", "Uninviting `$userId`...")

                            // FIXME: Errors for this aren't displayed as I don't have any idea of where to place the relevant text on the UI, but also are so unlikely to occour that it should be ok to disregard it
                            try {
                                room.membershipService().remove(userId)
                            } catch (e: Throwable) {
                                Log.e("Room", "Failed to uninvite `$userId`: $e")
                                return@SendUninvite
                            }

                            Log.d("Room", "Successfully uninvited `$userId`!")
                        }
                    },
                )
            }
        }
    }
}
