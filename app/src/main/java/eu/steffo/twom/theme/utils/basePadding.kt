package eu.steffo.twom.theme.utils

import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


fun Modifier.basePadding(): Modifier {
    return this.padding(all = 10.dp)
}
