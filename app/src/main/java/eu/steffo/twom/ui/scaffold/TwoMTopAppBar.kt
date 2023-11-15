package eu.steffo.twom.ui.scaffold

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import eu.steffo.twom.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TwoMTopAppBar(
    content: @Composable (innerPadding: PaddingValues) -> Unit = {},
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(LocalContext.current.getString(R.string.app_name)) }
            )
        }
    ) {
        content(it)
    }
}