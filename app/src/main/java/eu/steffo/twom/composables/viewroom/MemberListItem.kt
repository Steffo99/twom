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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import eu.steffo.twom.R
import eu.steffo.twom.composables.avatar.AvatarUser
import eu.steffo.twom.composables.errorhandling.ErrorText
import eu.steffo.twom.composables.matrix.LocalSession
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
        ErrorText(stringResource(R.string.error_session_missing))
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

    // TODO: Is this necessary?

    var user by remember { mutableStateOf<User?>(null) }

    LaunchedEffect(session, member.userId) {
        val memberId = member.userId
        Log.d("UserListItem", "Resolving user: $memberId")
        user = session.userService().resolveUser(memberId)
        Log.d("UserListItem", "Resolved user: $memberId")
    }

    val rsvp = observeRSVP(room = room, member = member)

    var expanded by rememberSaveable { mutableStateOf(false) }

    ListItem(
        modifier = modifier.combinedClickable(
            onClick = {},
            onLongClick = { expanded = true },
        ),
        headlineContent = {
            Text(
                text = user?.displayName ?: stringResource(R.string.user_unresolved_name),
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
                )
            }
        },
        trailingContent = {
            Icon(
                imageVector = rsvp.answer.icon,
                contentDescription = rsvp.answer.toResponse(),
                tint = rsvp.answer.staticColorRole.color(),
            )
        },
        supportingContent = {
            if (rsvp.comment != "") {
                Text(
                    text = rsvp.comment,
                    color = rsvp.answer.staticColorRole.color(),
                )
            }
        },
    )

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {
        DropdownMenuItem(
            text = {
                Text(stringResource(R.string.room_uninvite_label))
            },
            onClick = { expanded = false }
        )
    }
}