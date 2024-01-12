package eu.steffo.twom.composables.viewroom

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import eu.steffo.twom.utils.RSVPAnswer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun RSVPChip(
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    onClick: () -> Unit = {},
    representedAnswer: RSVPAnswer = RSVPAnswer.UNKNOWN,
) {
    FilterChip(
        modifier = modifier,
        selected = selected,
        onClick = onClick,
        leadingIcon = {
            Icon(
                imageVector = representedAnswer.icon,
                contentDescription = null,
            )
        },
        label = {
            Text(
                text = representedAnswer.toLabel() ?: "[missing label]",
                style = MaterialTheme.typography.bodyLarge,
            )
        },
        colors = FilterChipDefaults.filterChipColors(
            iconColor = representedAnswer.staticColorRole.color(),
            labelColor = representedAnswer.staticColorRole.color(),
            selectedContainerColor = representedAnswer.staticColorRole.containerColor(),
            selectedLeadingIconColor = representedAnswer.staticColorRole.onContainerColor(),
            selectedLabelColor = representedAnswer.staticColorRole.onContainerColor(),
        ),
        border = FilterChipDefaults.filterChipBorder(
            borderColor = MaterialTheme.colorScheme.surfaceVariant,
            selectedBorderColor = representedAnswer.staticColorRole.onContainerColor(),
            borderWidth = 1.dp,
            selectedBorderWidth = 1.dp,
        )
    )
}