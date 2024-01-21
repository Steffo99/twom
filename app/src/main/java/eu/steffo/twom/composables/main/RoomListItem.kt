package eu.steffo.twom.composables.main

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import eu.steffo.twom.R
import eu.steffo.twom.activities.ViewRoomActivity
import eu.steffo.twom.composables.avatar.AvatarURL
import eu.steffo.twom.composables.errorhandling.ErrorText
import eu.steffo.twom.composables.errorhandling.LocalizableError
import eu.steffo.twom.composables.matrix.LocalSession
import kotlinx.coroutines.launch
import org.matrix.android.sdk.api.session.room.model.RoomSummary


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RoomListItem(
    roomSummary: RoomSummary,
) {
    val roomId = roomSummary.roomId

    val scope = rememberCoroutineScope()

    val session = LocalSession.current
    if (session == null) {
        ErrorText(
            text = stringResource(R.string.error_session_missing)
        )
        return
    }

    var expanded by rememberSaveable { mutableStateOf(false) }
    val error by remember { mutableStateOf(LocalizableError()) }

    val viewRoomActivityLauncher = rememberLauncherForActivityResult(ViewRoomActivity.Contract()) {}

    fun openRoom() {
        Log.i("Main", "Opening room `$roomId`...")
        viewRoomActivityLauncher.launch(roomId)
    }

    suspend fun leaveRoom() {
        Log.i("Main", "Leaving room `$roomId`...")
        try {
            session.roomService().leaveRoom(roomId, "Decided to leave the room")
        } catch (e: Throwable) {
            Log.e("Main", "Failed to leave room `$roomId`: $error")
            error.set(R.string.main_error_leave_generic, e)
            return
        }
        Log.d("Main", "Successfully left room `$roomId`!")
    }

    Box {

        ListItem(
            modifier = Modifier.combinedClickable(
                onClick = { openRoom() },
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
                    AvatarURL(
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
                    scope.launch { leaveRoom() }
                }
            )
        }

    }
}