package eu.steffo.twom.main

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import eu.steffo.twom.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun MainActivityTopBar(
    modifier: Modifier = Modifier,
    onClickLogin: () -> Unit = {},
    onClickLogout: () -> Unit = {},
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {
            Text(LocalContext.current.getString(R.string.app_name))
        },
        actions = {
            MainActivityAccountIconButton(
                onClickLogin = onClickLogin,
                onClickLogout = onClickLogout,
            )
        },
    )
}