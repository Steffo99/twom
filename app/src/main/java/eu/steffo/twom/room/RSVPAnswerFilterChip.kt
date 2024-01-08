package eu.steffo.twom.room

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RSVPAnswerFilterChip(
    modifier: Modifier = Modifier,
    representing: RSVPAnswer,
    selected: Boolean = false,
    onClick: () -> Unit = {},
) {
    val icon = representing.toIcon()
    val colorRole = representing.toStaticColorRole()
    val labelResourceId = representing.toLabelResourceId()

    FilterChip(
        modifier = modifier,
        selected = selected,
        onClick = onClick,
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = null,
            )
        },
        label = {
            Text(
                text = stringResource(labelResourceId),
                style = MaterialTheme.typography.bodyLarge,
            )
        },
        colors = FilterChipDefaults.filterChipColors(
            iconColor = colorRole.value,
            labelColor = colorRole.value,
            selectedContainerColor = colorRole.valueContainer,
            selectedLeadingIconColor = colorRole.onValueContainer,
            selectedLabelColor = colorRole.onValueContainer,
        ),
        border = FilterChipDefaults.filterChipBorder(
            borderColor = MaterialTheme.colorScheme.surfaceVariant,
            selectedBorderColor = colorRole.onValueContainer,
            borderWidth = 1.dp,
            selectedBorderWidth = 1.dp,
        )
    )
}