package eu.steffo.twom.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import eu.steffo.twom.R
import eu.steffo.twom.theme.TwoMPadding
import org.matrix.android.sdk.api.session.Session

@Composable
fun MainActivityLoggedInControl(
    session: Session,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        Row(TwoMPadding.base) {
            Text(LocalContext.current.getString(R.string.loggedin_text))
        }
    }
}