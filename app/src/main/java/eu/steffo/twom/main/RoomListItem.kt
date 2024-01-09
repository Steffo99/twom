package eu.steffo.twom.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import eu.steffo.twom.R
import eu.steffo.twom.matrix.avatar.AvatarFromURL
import org.matrix.android.sdk.api.session.room.model.RoomSummary


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RoomListItem(
    roomSummary: RoomSummary,
    onClickRoom: (roomId: String) -> Unit = {},
    onLeaveRoom: (roomId: String) -> Unit = {},
) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    ListItem(
        modifier = Modifier.combinedClickable(
            onClick = { onClickRoom(roomSummary.roomId) },
            onLongClick = { expanded = true }
        ),
        headlineContent = {
            Text(roomSummary.displayName)
        },
        leadingContent = {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(MaterialTheme.shapes.medium)
            ) {
                AvatarFromURL(
                    // FIXME: URL can appearently be set before the image is available on the homeserver
                    url = roomSummary.avatarUrl,
                )
            }
        },
        supportingContent = {
            // TODO: Display rsvpComment instead of alias
            val canonicalAlias = roomSummary.canonicalAlias
            if (canonicalAlias != null) {
                Text(canonicalAlias)
            }
        },
    )

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false },
    ) {
        // TODO: Align me to the right
        DropdownMenuItem(
            text = {
                Text(stringResource(id = R.string.main_room_leave_label))
            },
            onClick = {
                expanded = false
                onLeaveRoom(roomSummary.roomId)
            }
        )
    }
}