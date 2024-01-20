package eu.steffo.twom.composables.viewroom

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
import eu.steffo.twom.composables.errorhandling.ErrorText
import eu.steffo.twom.composables.errorhandling.LoadingText
import eu.steffo.twom.composables.errorhandling.LocalizableError
import eu.steffo.twom.composables.theme.basePadding
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
            text = stringResource(R.string.room_error_room_notfound)
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
                text = stringResource(R.string.room_invite_username_label)
            )
        },
        placeholder = {
            Text(
                text = stringResource(R.string.room_invite_username_placeholder)
            )
        },
    )

    var busy by rememberSaveable { mutableStateOf(false) }
    val error by remember { mutableStateOf(LocalizableError()) }

    Button(
        modifier = Modifier
            .basePadding()
            .fillMaxWidth(),
        // FIXME: Maybe I should validate usernames with a regex
        enabled = (!busy && userId.contains("@") && userId.contains(":")),
        onClick = {
            scope.launch SendInvite@{
                busy = true
                error.clear()

                Log.d("Room", "Sending invite to `$userId`...")

                try {
                    room.membershipService().invite(userId)
                } catch (e: Throwable) {
                    Log.e("Room", "Failed to send invite to `$userId`: $error")
                    error.set(R.string.room_error_invite_generic, e)
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
            text = stringResource(R.string.room_invite_button_label)
        )
    }

    error.Show {
        ErrorText(
            modifier = Modifier
                .basePadding()
                .fillMaxWidth(),
            text = it,
        )
    }
}