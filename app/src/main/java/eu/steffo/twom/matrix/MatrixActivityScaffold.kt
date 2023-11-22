package eu.steffo.twom.matrix

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import eu.steffo.twom.R
import eu.steffo.twom.theme.TwoMTheme
import org.matrix.android.sdk.api.session.Session

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun MatrixActivityScaffold(
    onClickLogin: () -> Unit = {},
    session: Session? = null,
) {
    TwoMTheme {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(LocalContext.current.getString(R.string.app_name))
                    },
                    actions = {
                        if (session != null) {
                            IconButton(
                                onClick = {}
                            ) {
                                Icon(
                                    Icons.Filled.AccountCircle,
                                    LocalContext.current.getString(R.string.account_label)
                                )
                            }
                        }
                    },
                )
            }
        ) {
            if (session != null) {
                MatrixActivityLoggedInControl(
                    modifier = Modifier.padding(it),
                    session = session,
                )
            } else {
                MatrixActivityNotLoggedInControl(
                    modifier = Modifier.padding(it),
                    onClickLogin = onClickLogin,
                )
            }
        }
    }
}