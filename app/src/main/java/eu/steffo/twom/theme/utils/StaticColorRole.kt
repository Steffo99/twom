package eu.steffo.twom.theme.utils

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import com.google.android.material.color.MaterialColors


interface StaticColorRole {
    val lightColor: Color
    val lightOnColor: Color
    val lightContainerColor: Color
    val lightOnContainerColor: Color

    val darkColor: Color
    val darkOnColor: Color
    val darkContainerColor: Color
    val darkOnContainerColor: Color

    @Composable
    fun harmonize(color: Color): Color {
        val ctx = LocalContext.current
        val colorArgb = color.toArgb()
        val colorArgbHarmonized = MaterialColors.harmonizeWithPrimary(ctx, colorArgb)
        return Color(colorArgbHarmonized)
    }

    @Composable
    fun color(): Color {
        return harmonize(
            when (isSystemInDarkTheme()) {
                false -> lightColor
                true -> darkColor
            }
        )
    }

    @Composable
    fun onColor(): Color {
        return harmonize(
            when (isSystemInDarkTheme()) {
                false -> lightOnColor
                true -> darkOnColor
            }
        )
    }

    @Composable
    fun containerColor(): Color {
        return harmonize(
            when (isSystemInDarkTheme()) {
                false -> lightContainerColor
                true -> darkContainerColor
            }
        )
    }

    @Composable
    fun onContainerColor(): Color {
        return harmonize(
            when (isSystemInDarkTheme()) {
                false -> lightOnContainerColor
                true -> darkOnContainerColor
            }
        )
    }
}