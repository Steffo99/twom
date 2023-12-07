package eu.steffo.twom.create

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import eu.steffo.twom.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun CreateActivityTopBar(
    modifier: Modifier = Modifier,
    onClickBack: () -> Unit = {},
) {
    TopAppBar(
        modifier = modifier,
        navigationIcon = {
            IconButton(onClick = onClickBack) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = LocalContext.current.getString(R.string.back)
                )
            }
        },
        title = { Text(LocalContext.current.getString(R.string.create_title)) }
    )
}