package eu.steffo.twom.room

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import eu.steffo.twom.R
import eu.steffo.twom.theme.colorRoleLater
import eu.steffo.twom.theme.iconRoleLater

@Composable
@Preview
fun RoomActivityChipLater(
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    onClick: () -> Unit = {},
) {
    RoomActivityChip(
        selected = selected,
        onClick = onClick,
        imageVector = iconRoleLater,
        text = stringResource(R.string.room_rsvp_later_label),
        colorRole = colorRoleLater(),
    )
}
