package eu.steffo.twom.ui.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import eu.steffo.twom.R
import eu.steffo.twom.ui.BASE_PADDING


@Composable
@Preview(showBackground = true)
fun LoginActivityControl(
    modifier: Modifier = Modifier,
    onSelectHomeserver: () -> Unit = {},
    onComplete: () -> Unit = {},
) {
    Column(modifier) {
        Row(BASE_PADDING) {
            Text(LocalContext.current.getString(R.string.login_text))
        }
        Row(BASE_PADDING) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = onSelectHomeserver,
                enabled = true,
            ) {
                Text(LocalContext.current.getString(R.string.login_selecthomeserver_text))
            }
        }
        Row(BASE_PADDING) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = "",
                onValueChange = {},
                placeholder = {
                    Text(LocalContext.current.getString(R.string.login_username_placeholder))
                },
                supportingText = {
                    Text(LocalContext.current.getString(R.string.login_username_supporting))
                },
            )
        }
        Row(BASE_PADDING) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = "",
                onValueChange = {},
                placeholder = {
                    Text(LocalContext.current.getString(R.string.login_password_placeholder))
                },
                supportingText = {
                    Text(LocalContext.current.getString(R.string.login_password_supporting))
                },
            )
        }
        Row(BASE_PADDING) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = onComplete,
                enabled = false,
            ) {
                Text(LocalContext.current.getString(R.string.login_complete_text))
            }
        }
    }
}