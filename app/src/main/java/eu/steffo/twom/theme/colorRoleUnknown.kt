package eu.steffo.twom.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun colorRoleUnknown(): StaticColorRole {
    return StaticColorRole(
        value = MaterialTheme.colorScheme.inverseSurface,
        onValue = MaterialTheme.colorScheme.inverseOnSurface,
        valueContainer = MaterialTheme.colorScheme.surfaceVariant,
        onValueContainer = MaterialTheme.colorScheme.onSurfaceVariant,
    )
}
