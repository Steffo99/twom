package eu.steffo.twom.composables.inviteuser

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import eu.steffo.twom.R

@Composable
@Preview
fun InviteUserForm(
    onConfirm: (userId: String) -> Unit = {},
) {
    var value by rememberSaveable { mutableStateOf("") }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(),
        value = value,
        onValueChange = { value = it },
        singleLine = true,
        shape = MaterialTheme.shapes.small,
        placeholder = {
            Text(
                text = stringResource(R.string.room_invite_username_placeholder)
            )
        },
    )

    Button(
        modifier = Modifier
            .padding(top = 4.dp)
            .fillMaxWidth(),
        onClick = { onConfirm(value) },
        shape = MaterialTheme.shapes.small,
        // FIXME: Maybe I should validate usernames with a regex
        enabled = (value.contains("@") && value.contains(":")),
    ) {
        Text(
            text = stringResource(R.string.room_invite_button_label)
        )
    }
}