package eu.steffo.twom.main.components

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.launch
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.net.toFile
import eu.steffo.twom.R
import eu.steffo.twom.configureroom.activities.ConfigureRoomActivity
import eu.steffo.twom.matrix.complocals.LocalSession
import eu.steffo.twom.matrix.utils.TwoMMatrix
import kotlinx.coroutines.launch
import org.matrix.android.sdk.api.session.room.model.create.CreateRoomParams
import org.matrix.android.sdk.api.session.room.model.create.CreateRoomPreset
import org.matrix.android.sdk.api.session.room.model.create.CreateRoomStateEvent

private const val TAG = "CreateRoomFAB"

@Composable
@Preview
fun CreateRoomFAB(
    modifier: Modifier = Modifier,
) {
    val scope = rememberCoroutineScope()

    // Hide if no session is available
    val session = LocalSession.current ?: return

    val launcher =
        rememberLauncherForActivityResult(ConfigureRoomActivity.CreateContract()) CreateRoom@{
            it ?: return@CreateRoom

            scope.launch {
                Log.d(TAG, "Configuring room creation parameters...")
                val createRoomParams = CreateRoomParams()

                createRoomParams.name = it.name
                Log.d(TAG, "Room name is: ${createRoomParams.name}")

                createRoomParams.topic = it.description
                Log.d(TAG, "Room description is: ${createRoomParams.topic}")

                createRoomParams.preset = CreateRoomPreset.PRESET_PRIVATE_CHAT
                Log.d(TAG, "Room preset is: ${createRoomParams.preset}")

                createRoomParams.roomType = TwoMMatrix.ROOM_TYPE
                Log.d(TAG, "Room type is: ${createRoomParams.roomType}")

                when (it.avatarUri?.toFile()?.isFile) {
                    false -> {
                        Log.e(TAG, "Avatar is no longer in cache, not setting...")
                    }

                    true -> {
                        createRoomParams.avatarUri = it.avatarUri
                        Log.d(TAG, "Room avatar is: ${createRoomParams.avatarUri}")
                    }

                    null -> {
                        Log.d(TAG, "Room avatar is: <not set>")
                    }
                }

                Log.v(TAG, "Constructing initial power levels event...")
                val powerLevelsEvent = CreateRoomStateEvent(
                    type = "m.room.power_levels",
                    content = mapOf(
                        // Users start with a power level of 0
                        "users_default" to 0,
                        // Allow only the party creator to send arbitrary events
                        "events_default" to 100,
                        // Allow only the party creator to send arbitrary states
                        "state_default" to 100,

                        // Allow only party officers to send invites
                        "invite" to 50,
                        // Allow only party officers to kick invitees
                        "kick" to 50,
                        // Allow only party officers to ban invitees
                        "ban" to 50,
                        // Allow only party officers to redact other people's events
                        "redact" to 50,

                        "notifications" to mapOf(
                            // Allow only party officers to ping the room
                            "room" to 50,
                        ),

                        "events" to mapOf(
                            // Allow party officers to rename the room
                            "m.room.name" to 50,
                            // Allow party officers to change the room avatar
                            "m.room.avatar" to 50,
                            // Allow party officers to change the room topic
                            "m.room.topic" to 50,
                            // Allow everyone to redact their own states
                            "m.room.redaction" to 0,
                            // Allow everyone to set RSVPs
                            // Do we really want everyone to set RSVPs? Maybe m.room.member could be used instead...?
                            "eu.steffo.twom.rsvp" to 0,
                        ),

                        "users" to mapOf(
                            // Give ourselves admin permissions
                            session.myUserId to 100,
                        )
                    )
                )
                Log.v(TAG, "Initial power levels event is: $powerLevelsEvent")

                createRoomParams.initialStates = mutableListOf(
                    powerLevelsEvent
                )
                Log.d(TAG, "Initial states are: ${createRoomParams.initialStates}")

                Log.i(TAG, "Creating room: ${createRoomParams.name}")
                val roomId = session.roomService().createRoom(createRoomParams)

                Log.d(TAG, "Successfully created room: $roomId")
            }
        }

    ExtendedFloatingActionButton(
        modifier = modifier,
        onClick = { launcher.launch() },
        icon = {
            Icon(
                Icons.Filled.Add,
                contentDescription = null
            )
        },
        text = {
            Text(stringResource(R.string.createroom_label))
        }
    )
}