package eu.steffo.twom.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.google.android.material.color.MaterialColors


@Composable
fun colorRoleMaybe(): StaticColorRole {
    val ctx = LocalContext.current

    return when (isSystemInDarkTheme()) {
        false -> StaticColorRole(
            value = Color(MaterialColors.harmonizeWithPrimary(ctx, 0x765B00)),
            onValue = Color(MaterialColors.harmonizeWithPrimary(ctx, 0xFFFFFF)),
            valueContainer = Color(MaterialColors.harmonizeWithPrimary(ctx, 0xFFDF94)),
            onValueContainer = Color(MaterialColors.harmonizeWithPrimary(ctx, 0x241A00)),
        )

        true -> StaticColorRole(
            value = Color(MaterialColors.harmonizeWithPrimary(ctx, 0xEDC148)),
            onValue = Color(MaterialColors.harmonizeWithPrimary(ctx, 0x3E2E00)),
            valueContainer = Color(MaterialColors.harmonizeWithPrimary(ctx, 0x594400)),
            onValueContainer = Color(MaterialColors.harmonizeWithPrimary(ctx, 0xFFDF94)),
        )
    }
}
