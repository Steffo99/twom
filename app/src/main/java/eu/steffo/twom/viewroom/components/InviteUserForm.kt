package eu.steffo.twom.viewroom.components

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import eu.steffo.twom.errorhandling.components.ErrorText
import eu.steffo.twom.errorhandling.components.LoadingText
import eu.steffo.twom.errorhandling.utils.Display
import eu.steffo.twom.errorhandling.utils.LocalizableError
import eu.steffo.twom.theme.utils.basePadding
import eu.steffo.twom.viewroom.complocals.LocalRoom
import kotlinx.coroutines.launch
import kotlin.jvm.optionals.getOrNull

@Composable
fun InviteUserForm(
    onDone: () -> Unit = {},
) {
    val scope = rememberCoroutineScope()

    var userId by rememberSaveable { mutableStateOf("") }

    val roomRequest = LocalRoom.current
    if (roomRequest == null) {
        LoadingText(
            modifier = Modifier.basePadding(),
        )
        return
    }

    val room = roomRequest.getOrNull()
    if (room == null) {
        ErrorText(
            modifier = Modifier.basePadding(),
            text = stringResource(R.string.invite_error_room_notfound)
        )
        return
    }

    TextField(
        modifier = Modifier
            .basePadding()
            .fillMaxWidth(),
        value = userId,
        onValueChange = { userId = it },
        singleLine = true,
        label = {
            Text(
                text = stringResource(R.string.invite_username_label)
            )
        },
        placeholder = {
            Text(
                text = stringResource(R.string.invite_username_placeholder)
            )
        },
    )

    var busy by rememberSaveable { mutableStateOf(false) }
    var error by remember { mutableStateOf<LocalizableError?>(null) }

    Button(
        modifier = Modifier
            .basePadding()
            .fillMaxWidth(),
        // A cool thing to do would be resolving the userId exists on the server side, and display the avatar if it does resolve successfully...
        enabled = (!busy && userId.contains("@") && userId.contains(":")),
        onClick = {
            scope.launch SendInvite@{
                busy = true
                error = null

                Log.d("Room", "Sending invite to `$userId`...")

                try {
                    room.membershipService().invite(userId)
                } catch (e: Throwable) {
                    Log.e("Room", "Failed to send invite to `$userId`: $error")
                    error = LocalizableError(R.string.invite_error_invite_generic, e)
                    busy = false
                    return@SendInvite
                }

                Log.d("Room", "Successfully sent invite to `$userId`!")
                onDone()

                busy = false
            }
        },
    ) {
        Text(
            text = stringResource(R.string.invite_submit_label)
        )
    }

    error.Display {
        ErrorText(
            modifier = Modifier
                .basePadding()
                .fillMaxWidth(),
            text = it,
        )
    }
}