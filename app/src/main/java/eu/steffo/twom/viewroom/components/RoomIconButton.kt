package eu.steffo.twom.viewroom.components

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toFile
import eu.steffo.twom.R
import eu.steffo.twom.avatar.components.AvatarURL
import eu.steffo.twom.configureroom.activities.ConfigureRoomActivity
import eu.steffo.twom.errorhandling.components.ErrorIconButton
import eu.steffo.twom.matrix.complocals.LocalSession
import eu.steffo.twom.viewroom.complocals.LocalRoom
import eu.steffo.twom.viewroom.complocals.LocalRoomSummary
import kotlinx.coroutines.launch
import kotlin.jvm.optionals.getOrNull

@Composable
fun RoomIconButton(
    modifier: Modifier = Modifier,
    canEdit: Boolean = true,
) {
    var expanded by remember { mutableStateOf(false) }

    val session = LocalSession.current
    if (session == null) {
        ErrorIconButton(
            message = stringResource(R.string.error_session_missing)
        )
        return
    }

    val roomSummaryRequest = LocalRoomSummary.current
    if (roomSummaryRequest == null) {
        CircularProgressIndicator()
        return
    }

    val roomSummary = roomSummaryRequest.getOrNull()
    if (roomSummary == null) {
        ErrorIconButton(
            message = stringResource(R.string.room_error_roomsummary_notfound)
        )
        return
    }

    val roomRequest = LocalRoom.current
    if (roomRequest == null) {
        CircularProgressIndicator()
        return
    }

    val room = roomRequest.getOrNull()
    if (room == null) {
        ErrorIconButton(
            message = stringResource(R.string.invite_error_room_notfound)
        )
        return
    }

    val scope = rememberCoroutineScope()

    val launcher =
        rememberLauncherForActivityResult(
            ConfigureRoomActivity.EditContract()
        ) {
            if (it != null) {
                scope.launch {

                    val name = it.name
                    Log.d("RoomIconButton", "Updating room name to `$name`")
                    room.stateService().updateName(it.name)

                    val description = it.description
                    Log.d("RoomIconButton", "Updating room description to `$description`")
                    room.stateService().updateTopic(it.description)

                    val avatarUri = it.avatarUri
                    val avatarFile = avatarUri?.toFile()
                    when (avatarFile?.isFile) {
                        false -> {
                            Log.e(
                                "RoomIconButton",
                                "Avatar has been deleted from cache before room could possibly be updated, ignoring..."
                            )
                        }

                        true -> {
                            Log.d(
                                "RoomIconButton",
                                "Avatar seems to exist at: $avatarUri"
                            )
                            room.stateService().updateAvatar(avatarUri, avatarFile.name)
                        }

                        null -> {
                            Log.d(
                                "RoomIconButton",
                                "Avatar was not set, ignoring..."
                            )
                        }
                    }
                }
            }
        }

    // This padding is needed to prevent the UnclippedIconButtonLike from sticking to the side of the screen
    Box(modifier.padding(5.dp)) {
        UnclippedIconButtonlike(
            modifier = modifier
                .clip(MaterialTheme.shapes.medium),
            onClick = { expanded = true },
        ) {
            AvatarURL(
                url = roomSummary.avatarUrl,
                contentDescription = LocalContext.current.getString(R.string.roommenu_label),
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            if (canEdit) {
                DropdownMenuItem(
                    text = {
                        Text(stringResource(R.string.roommenu_edit_label))
                    },
                    onClick = {
                        expanded = false
                        launcher.launch(
                            ConfigureRoomActivity.Configuration(
                                name = roomSummary.name,
                                description = roomSummary.topic,
                                avatarUri = Uri.parse(roomSummary.avatarUrl),
                            )
                        )
                    }
                )
            }
        }
    }
}
