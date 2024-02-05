package eu.steffo.twom.main.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import eu.steffo.twom.R
import eu.steffo.twom.theme.utils.basePadding

@Composable
@Preview(showBackground = true)
fun MainContentNotLoggedIn(
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        Row(Modifier.basePadding()) {
            Text(stringResource(R.string.main_notloggedin_intro_1))
        }
        Row(Modifier.basePadding()) {
            Text(stringResource(R.string.main_notloggedin_intro_2))
        }
    }
}