package eu.steffo.twom.composables.theme

import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


fun Modifier.basePadding(): Modifier {
    return this.padding(all = 10.dp)
}

fun Modifier.chipPadding(): Modifier {
    return this.padding(start = 2.5.dp, end = 2.5.dp)
}
