package eu.steffo.twom.composables.main

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.launch
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
import eu.steffo.twom.activities.CreateRoomActivity

@Composable
@Preview
fun CreateRoomFAB(
    modifier: Modifier = Modifier,
    onCreateParamsSelected: (name: String, description: String, avatarUri: String?) -> Unit = { _, _, _ -> },
) {
    val launcher =
        rememberLauncherForActivityResult(CreateRoomActivity.Contract()) {
            if (it != null) {
                onCreateParamsSelected(it.name, it.description, it.avatarUri)
            }
        }

    ExtendedFloatingActionButton(
        modifier = modifier,
        onClick = { launcher.launch() },
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