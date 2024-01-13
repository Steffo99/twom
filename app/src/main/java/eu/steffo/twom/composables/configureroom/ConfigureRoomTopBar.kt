package eu.steffo.twom.composables.configureroom

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import eu.steffo.twom.R
import eu.steffo.twom.composables.navigation.BackIconButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun CreateActivityTopBar(
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        modifier = modifier,
        navigationIcon = { BackIconButton() },
        title = { Text(LocalContext.current.getString(R.string.create_title)) }
    )
}