package eu.steffo.twom.avatar.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import eu.steffo.twom.R

@Composable
@Preview(name = "Regular", widthDp = 40, heightDp = 40)
@Preview(name = "Double font scale", widthDp = 40, heightDp = 40, fontScale = 2f)
@Preview(name = "Quadruple font scale", widthDp = 40, heightDp = 40, fontScale = 4f)
fun AvatarEmpty(
    modifier: Modifier = Modifier,
    text: String? = null,
    alpha: Float = 1.0f,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.tertiary.copy(alpha = alpha)),
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Center),
            color = MaterialTheme.colorScheme.onTertiary.copy(alpha = alpha),
            text = text ?: stringResource(R.string.avatar_empty_placeholder),
        )
    }
}
