package eu.steffo.twom.viewroom.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import eu.steffo.twom.R

@Composable
@Preview
fun InviteFAB(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    // TODO: Hide if unprivileged
    ExtendedFloatingActionButton(
        modifier = modifier,
        onClick = { onClick() },
        icon = {
            Icon(
                Icons.Filled.Email,
                contentDescription = null
            )
        },
        text = {
            Text(stringResource(R.string.room_invite_button_label))
        }
    )
}