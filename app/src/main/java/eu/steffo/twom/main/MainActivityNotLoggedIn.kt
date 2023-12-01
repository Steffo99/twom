package eu.steffo.twom.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import eu.steffo.twom.R
import eu.steffo.twom.theme.TwoMPadding

@Composable
@Preview(showBackground = true)
fun MainActivityNotLoggedIn(
    modifier: Modifier = Modifier,
    onClickLogin: () -> Unit = {},
) {
    Column(modifier) {
        Row(TwoMPadding.base) {
            Text(LocalContext.current.getString(R.string.main_notloggedin_text_1))
        }
        Row(TwoMPadding.base) {
            Text(LocalContext.current.getString(R.string.main_notloggedin_text_2))
        }
    }
}