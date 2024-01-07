package eu.steffo.twom.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.google.android.material.color.MaterialColors


@Composable
fun colorRoleNoway(): StaticColorRole {
    val ctx = LocalContext.current

    return when (isSystemInDarkTheme()) {
        false -> StaticColorRole(
            value = Color(MaterialColors.harmonizeWithPrimary(ctx, 0xAB3520)),
            onValue = Color(MaterialColors.harmonizeWithPrimary(ctx, 0xFFFFFF)),
            valueContainer = Color(MaterialColors.harmonizeWithPrimary(ctx, 0xFFDAD3)),
            onValueContainer = Color(MaterialColors.harmonizeWithPrimary(ctx, 0x3F0400)),
        )

        true -> StaticColorRole(
            value = Color(MaterialColors.harmonizeWithPrimary(ctx, 0xFFB4A5)),
            onValue = Color(MaterialColors.harmonizeWithPrimary(ctx, 0x650A00)),
            valueContainer = Color(MaterialColors.harmonizeWithPrimary(ctx, 0x891D0A)),
            onValueContainer = Color(MaterialColors.harmonizeWithPrimary(ctx, 0xFFDAD3)),
        )
    }
}
