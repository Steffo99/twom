package eu.steffo.twom.composables.errorhandling

import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import eu.steffo.twom.R


@Composable
@Preview
fun LoadingText(
    modifier: Modifier = Modifier,
    text: String? = null,
    style: TextStyle = LocalTextStyle.current,
) {
    Text(
        modifier = modifier,
        text = text ?: stringResource(R.string.loading),
        color = LocalContentColor.current.copy(0.4f),
        style = style,
    )
}
