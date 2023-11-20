package eu.steffo.twom.ui.login

import android.content.Intent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import eu.steffo.twom.R
import eu.steffo.twom.ui.theme.TwoMTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun LoginActivityScaffold(
    onBack: () -> Unit = {},
    onSelectHomeserver: () -> Unit = {},
) {
    TwoMTheme {
        Scaffold(
            topBar = {
                TopAppBar (
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
        ) {
            LoginActivityControl(
                modifier = Modifier.padding(it),
                onSelectHomeserver = onSelectHomeserver,
            )
        }
    }
}