package eu.steffo.twom.composables.inviteuser

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import eu.steffo.twom.R
import eu.steffo.twom.composables.theme.basePadding

@Composable
fun InviteUserContent() {
    Row(Modifier.basePadding()) {
        Text(
            text = stringResource(R.string.room_invite_title),
            style = MaterialTheme.typography.labelLarge,
        )
    }

    Row(Modifier.basePadding()) {
        InviteUserForm(
            /*
            onConfirm = {
                scope.launch SendInvite@{
                    isSendingInvite = true
                    errorInvite = null

                    Log.d("Room", "Sending invite to `$it`...")

                    try {
                        room.membershipService().invite(it)
                    } catch (error: Throwable) {
                        Log.e("Room", "Failed to send invite to `$it`: $error")
                        errorInvite = error
                        isSendingInvite = false
                        return@SendInvite
                    }

                    Log.d("Room", "Successfully sent invite to `$it`!")
                    isSendingInvite = false
                }
            }
             */
        )
    }
}