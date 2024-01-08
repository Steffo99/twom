package eu.steffo.twom.theme

import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable

@Composable
fun colorRoleUnknown(): StaticColorRole {
    return StaticColorRole(
        value = LocalContentColor.current,
        onValue = LocalContentColor.current,
        valueContainer = LocalContentColor.current,
        onValueContainer = LocalContentColor.current,
    )
}
