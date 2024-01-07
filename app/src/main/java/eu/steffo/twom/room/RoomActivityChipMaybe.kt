package eu.steffo.twom.room

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import eu.steffo.twom.R
import eu.steffo.twom.theme.colorRoleMaybe
import eu.steffo.twom.theme.iconRoleMaybe

@Composable
@Preview
fun RoomActivityChipMaybe(
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    onClick: () -> Unit = {},
) {
    // TODO: Pick a better color
    RoomActivityChip(
        selected = selected,
        onClick = onClick,
        imageVector = iconRoleMaybe,
        text = stringResource(R.string.room_rsvp_maybe_label),
        colorRole = colorRoleMaybe(),
    )
}
