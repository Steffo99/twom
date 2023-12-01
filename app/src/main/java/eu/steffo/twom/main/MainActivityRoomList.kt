package eu.steffo.twom.main

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import eu.steffo.twom.R
import eu.steffo.twom.matrix.LocalSession
import eu.steffo.twom.theme.TwoMPadding
import org.matrix.android.sdk.api.session.room.model.RoomSummary
import org.matrix.android.sdk.api.session.room.roomSummaryQueryParams

@Composable
fun MainActivityRoomList(
    modifier: Modifier = Modifier,
    onClickRoom: (roomId: String) -> Unit = {},
) {
    val session = LocalSession.current
    var roomSummaries by remember { mutableStateOf<List<RoomSummary>?>(null) }

    LaunchedEffect(session) GetRoomSummaries@{
        if (session == null) {
            Log.d("RoomList", "Not doing anything, session is null.")
            return@GetRoomSummaries
        }
        Log.d("RoomList", "Getting room summaries...")
        val queryParamsBuilder = roomSummaryQueryParams()
        roomSummaries = session.roomService().getRoomSummaries(queryParamsBuilder)
        Log.d("RoomList", "Obtained room summaries: $roomSummaries")
    }

    Column(modifier) {
        if (roomSummaries == null) {
            Text(
                modifier = TwoMPadding.base,
                text = stringResource(R.string.loading)
            )
        } else if (roomSummaries!!.isEmpty()) {
            Text(
                modifier = TwoMPadding.base,
                text = stringResource(R.string.main_roomlist_empty_text)
            )
        } else {
            roomSummaries!!.forEach {
                RoomListItem(
                    onClickRoom = onClickRoom,
                    roomSummary = it,
                )
            }
        }
    }
}