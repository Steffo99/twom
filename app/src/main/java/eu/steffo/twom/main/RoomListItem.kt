package eu.steffo.twom.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import eu.steffo.twom.matrix.avatar.AvatarFromURL
import org.matrix.android.sdk.api.session.room.model.RoomSummary


@Composable
fun RoomListItem(
    roomSummary: RoomSummary,
    onClickRoom: (roomId: String) -> Unit,
) {
    ListItem(
        modifier = Modifier.clickable {
            onClickRoom(roomSummary.roomId)
        },
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
            val canonicalAlias = roomSummary.canonicalAlias
            if (canonicalAlias != null) {
                Text(canonicalAlias)
            }
        },
    )
}