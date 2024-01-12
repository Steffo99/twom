package eu.steffo.twom.composables.viewroom

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.launch
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import eu.steffo.twom.R
import eu.steffo.twom.activities.InviteUserActivity

@Composable
@Preview
fun InviteFAB(
    modifier: Modifier = Modifier,
    onUserSelected: (userId: String) -> Unit = {},
) {
    val launcher =
        rememberLauncherForActivityResult(InviteUserActivity.Contract()) {
            if (it != null) {
                onUserSelected(it)
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
            Text(stringResource(R.string.room_invite_button_label))
        }
    )
}