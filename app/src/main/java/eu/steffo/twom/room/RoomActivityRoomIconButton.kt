package eu.steffo.twom.room

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import eu.steffo.twom.R
import eu.steffo.twom.matrix.avatar.AvatarFromURL

@Composable
@Preview(showBackground = true)
fun RoomActivityRoomIconButton(
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier) {
        // Mostly copied from IconButton's source
        // TODO: Make sure accessibility works right
        // FIXME: This will need changes when Material 4 is released
        Box(
            modifier = Modifier
                .minimumInteractiveComponentSize()
                .size(40.dp)
                .clip(MaterialTheme.shapes.medium)
                .clickable(
                    role = Role.Button,
                    interactionSource = interactionSource,
                    indication = rememberRipple(
                        bounded = false,
                        radius = 28.dp
                    )
                ) { expanded = true },
        ) {
            AvatarFromURL(
                url = LocalRoom.current!!.avatarUrl,
                contentDescription = LocalContext.current.getString(R.string.room_options_label),
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            DropdownMenuItem(
                text = {
                    Text("garasauto")
                },
                onClick = {
                    expanded = false
                }
            )
        }
    }

}