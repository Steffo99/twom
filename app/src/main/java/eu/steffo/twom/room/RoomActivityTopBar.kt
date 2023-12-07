package eu.steffo.twom.room

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import eu.steffo.twom.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun RoomActivityTopBar(
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
    roomName: String = "{Room name}",
    roomAvatarURL: String? = null,
) {
    TopAppBar(
        modifier = modifier,
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = LocalContext.current.getString(R.string.back)
                )
            }
        },
        title = {
            Text(LocalRoom.current!!.name)
        },
        actions = {
            RoomActivityRoomIconButton()
        },
    )
}