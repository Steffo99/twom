package eu.steffo.twom.composables.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import eu.steffo.twom.R

@Composable
@Preview
fun CreateRoomFAB(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    ExtendedFloatingActionButton(
        modifier = modifier,
        onClick = onClick,
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