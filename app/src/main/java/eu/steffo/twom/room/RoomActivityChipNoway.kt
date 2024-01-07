package eu.steffo.twom.room

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import eu.steffo.twom.R
import eu.steffo.twom.theme.colorRoleNoway
import eu.steffo.twom.theme.iconRoleNoway

@Composable
@Preview
fun RoomActivityChipNoway(
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    onClick: () -> Unit = {},
) {
    RoomActivityChip(
        selected = selected,
        onClick = onClick,
        imageVector = iconRoleNoway,
        text = stringResource(R.string.room_rsvp_noway_label),
        colorRole = colorRoleNoway(),
    )
}
