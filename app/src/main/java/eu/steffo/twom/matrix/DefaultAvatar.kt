package eu.steffo.twom.matrix

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DefaultAvatar(
    modifier: Modifier = Modifier,
    contentDescription: String = "",
) {
    Image(
        modifier = Modifier,
        bitmap = TwoMMatrix.defaultAvatar,
        contentDescription = contentDescription,
    )
}