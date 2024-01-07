package eu.steffo.twom.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.google.android.material.color.MaterialColors

@Composable
fun colorRoleSure(): StaticColorRole {
    val ctx = LocalContext.current

    return when (isSystemInDarkTheme()) {
        false -> StaticColorRole(
            value = Color(MaterialColors.harmonizeWithPrimary(ctx, 0x006E2C)),
            onValue = Color(MaterialColors.harmonizeWithPrimary(ctx, 0xFFFFFF)),
            valueContainer = Color(MaterialColors.harmonizeWithPrimary(ctx, 0x7FFC95)),
            onValueContainer = Color(MaterialColors.harmonizeWithPrimary(ctx, 0x002108)),
        )

        true -> StaticColorRole(
            value = Color(MaterialColors.harmonizeWithPrimary(ctx, 0x62DF7C)),
            onValue = Color(MaterialColors.harmonizeWithPrimary(ctx, 0x003913)),
            valueContainer = Color(MaterialColors.harmonizeWithPrimary(ctx, 0x00531F)),
            onValueContainer = Color(MaterialColors.harmonizeWithPrimary(ctx, 0x7FFC95)),
        )
    }
}
