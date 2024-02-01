package eu.steffo.twom.theme.utils

import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

fun Modifier.chipPadding(): Modifier {
    return this.padding(start = 2.5.dp, end = 2.5.dp)
}
