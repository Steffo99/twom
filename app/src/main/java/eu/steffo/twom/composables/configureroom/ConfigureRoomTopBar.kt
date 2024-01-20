package eu.steffo.twom.composables.configureroom

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import eu.steffo.twom.R
import eu.steffo.twom.composables.navigation.BackIconButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun ConfigureActivityTopBar(
    modifier: Modifier = Modifier,
    initialName: String? = null,
) {
    TopAppBar(
        modifier = modifier,
        navigationIcon = { BackIconButton() },
        title = {
            Text(
                text = if (initialName == null) {
                    stringResource(R.string.create_title)
                } else {
                    stringResource(R.string.edit_title, initialName)
                }
            )
        }
    )
}