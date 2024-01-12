package eu.steffo.twom.composables.viewroom

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import eu.steffo.twom.R
import eu.steffo.twom.composables.avatar.AvatarURL

@Composable
fun RoomIconButton(
    modifier: Modifier = Modifier,
    avatarUrl: String? = null,
    canEdit: Boolean = true,
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier) {
        IconButton(
            onClick = { expanded = true },
        ) {
            AvatarURL(
                url = avatarUrl,
                contentDescription = LocalContext.current.getString(R.string.room_options_label),
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            DropdownMenuItem(
                text = {
                    Text(stringResource(R.string.room_options_zoom_text))
                },
                onClick = {
                    // TODO
                    expanded = false
                }
            )

            if (canEdit) {
                DropdownMenuItem(
                    text = {
                        Text(stringResource(R.string.room_options_edit_text))
                    },
                    onClick = {
                        // TODO
                        expanded = false
                    }
                )
            }
        }
    }

}