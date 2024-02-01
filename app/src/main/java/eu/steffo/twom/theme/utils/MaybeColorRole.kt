package eu.steffo.twom.theme.utils

import androidx.compose.ui.graphics.Color

object MaybeColorRole : StaticColorRole {
    override val lightColor = Color(0xFF765B00)
    override val lightOnColor = Color(0xFFFFFFFF)
    override val lightContainerColor = Color(0xFFFFDF94)
    override val lightOnContainerColor = Color(0xFF241A00)

    override val darkColor = Color(0xFFEDC148)
    override val darkOnColor = Color(0xFF3E2E00)
    override val darkContainerColor = Color(0xFF594400)
    override val darkOnContainerColor = Color(0xFFFFDF94)
}
