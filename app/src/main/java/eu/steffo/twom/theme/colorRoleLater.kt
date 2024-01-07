package eu.steffo.twom.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.google.android.material.color.MaterialColors


@Composable
fun colorRoleLater(): StaticColorRole {
    val ctx = LocalContext.current

    return when (isSystemInDarkTheme()) {
        false -> StaticColorRole(
            value = Color(MaterialColors.harmonizeWithPrimary(ctx, 0x00658B)),
            onValue = Color(MaterialColors.harmonizeWithPrimary(ctx, 0xFFFFFF)),
            valueContainer = Color(MaterialColors.harmonizeWithPrimary(ctx, 0xC4E7FF)),
            onValueContainer = Color(MaterialColors.harmonizeWithPrimary(ctx, 0x001E2C)),
        )

        true -> StaticColorRole(
            value = Color(MaterialColors.harmonizeWithPrimary(ctx, 0x7DD0FF)),
            onValue = Color(MaterialColors.harmonizeWithPrimary(ctx, 0x00344A)),
            valueContainer = Color(MaterialColors.harmonizeWithPrimary(ctx, 0x004C69)),
            onValueContainer = Color(MaterialColors.harmonizeWithPrimary(ctx, 0xC4E7FF)),
        )
    }
}
