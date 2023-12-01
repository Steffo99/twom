package eu.steffo.twom.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import eu.steffo.twom.matrix.LocalSession

@Composable
fun MainActivityContent(
    modifier: Modifier = Modifier,
    onClickRoom: (roomId: String) -> Unit = {},
) {
    val session = LocalSession.current

    if (session == null) {
        MainActivityNotLoggedIn(
            modifier = modifier,
        )
    } else {
        MainActivityRoomList(
            modifier = modifier,
            onClickRoom = onClickRoom,
        )
    }
}