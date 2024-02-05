package eu.steffo.twom.login.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import eu.steffo.twom.R
import eu.steffo.twom.navigation.components.BackIconButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun LoginActivityTopBar(
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        modifier = modifier,
        navigationIcon = { BackIconButton() },
        title = { Text(stringResource(R.string.login_title)) }
    )
}