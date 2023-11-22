package eu.steffo.twom.matrix

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import eu.steffo.twom.R
import eu.steffo.twom.theme.TwoMPadding

@Composable
@Preview(showBackground = true)
fun MatrixActivityNotLoggedInControl(
    modifier: Modifier = Modifier,
    onClickLogin: () -> Unit = {},
) {
    Column(modifier) {
        Row(TwoMPadding.base) {
            Text(LocalContext.current.getString(R.string.notloggedin_text))
        }
        Row(TwoMPadding.base) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = onClickLogin,
            ) {
                Text(LocalContext.current.getString(R.string.notloggedin_login_text))
            }
        }
    }
}