package eu.steffo.twom.room

import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import eu.steffo.twom.theme.StaticColorRole
import eu.steffo.twom.theme.TwoMPadding

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoomActivityChip(
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    onClick: () -> Unit = {},
    text: String,
    imageVector: ImageVector,
    colorRole: StaticColorRole,
) {
    ElevatedFilterChip(
        modifier = TwoMPadding.chips,
        selected = selected,
        onClick = onClick,
        leadingIcon = {
            Icon(
                imageVector = imageVector,
                contentDescription = null,
            )
        },
        label = {
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge,
            )
        },
        colors = FilterChipDefaults.elevatedFilterChipColors(
            iconColor = colorRole.value,
            labelColor = colorRole.value,
            selectedContainerColor = colorRole.valueContainer,
            selectedLeadingIconColor = colorRole.onValueContainer,
            selectedLabelColor = colorRole.onValueContainer,
        ),
    )
}