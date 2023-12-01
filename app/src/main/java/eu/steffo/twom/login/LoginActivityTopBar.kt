package eu.steffo.twom.login

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import eu.steffo.twom.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun LoginActivityTopBar(
    onBack: () -> Unit = {},
) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = LocalContext.current.getString(R.string.back)
                )
            }
        },
        title = { Text(LocalContext.current.getString(R.string.login_title)) }
    )
}