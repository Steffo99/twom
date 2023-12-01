package eu.steffo.twom.matrix.avatar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview

@Composable
@Preview(widthDp = 40, heightDp = 40)
fun AvatarFromDefault(
    modifier: Modifier = Modifier,
    fallbackText: String = "?",
    contentDescription: String = "",
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.tertiary),
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Center)
                .semantics {
                    this.contentDescription = ""
                },
            color = MaterialTheme.colorScheme.onTertiary,
            text = fallbackText,
        )
    }
}