package eu.steffo.twom.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import eu.steffo.twom.R
import eu.steffo.twom.matrix.LocalSession

@Composable
@Preview
fun MainActivityCreateFAB(
    modifier: Modifier = Modifier,
    onClickCreate: () -> Unit = {},
) {
    val session = LocalSession.current

    val shouldDisplay = (session != null || LocalView.current.isInEditMode)

    if (shouldDisplay) {
        ExtendedFloatingActionButton(
            modifier = modifier,
            onClick = onClickCreate,
            icon = {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = null
                )
            },
            text = {
                Text(stringResource(R.string.main_efab_create_text))
            }
        )
    }
}