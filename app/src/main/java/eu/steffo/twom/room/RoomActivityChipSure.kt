package eu.steffo.twom.room

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import eu.steffo.twom.R
import eu.steffo.twom.theme.colorRoleSure
import eu.steffo.twom.theme.iconRoleSure


@Composable
@Preview
fun RoomActivityChipSure(
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    onClick: () -> Unit = {},
) {
    RoomActivityChip(
        selected = selected,
        onClick = onClick,
        imageVector = iconRoleSure,
        text = stringResource(R.string.room_rsvp_sure_label),
        colorRole = colorRoleSure(),
    )
}
