package eu.steffo.twom.composables.theme

import androidx.compose.ui.graphics.Color

object NullishColorRole : StaticColorRole {
    override val lightColor = Color(0xFF666666)
    override val lightOnColor = Color(0xFFFFFFFF)
    override val lightContainerColor = Color(0xFFE6E6E6)
    override val lightOnContainerColor = Color(0xFF222222)

    override val darkColor = Color(0xFFDDDDDD)
    override val darkOnColor = Color(0xFF333333)
    override val darkContainerColor = Color(0xFF555555)
    override val darkOnContainerColor = Color(0xFFFFFFFF)
}
