package eu.steffo.twom.main

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import eu.steffo.twom.R
import eu.steffo.twom.matrix.LocalSession
import eu.steffo.twom.matrix.TwoMMatrix
import eu.steffo.twom.theme.TwoMPadding
import org.matrix.android.sdk.api.session.room.model.Membership
import org.matrix.android.sdk.api.session.room.roomSummaryQueryParams

@Composable
fun MainActivityRoomList(
    modifier: Modifier = Modifier,
    onClickRoom: (roomId: String) -> Unit = {},
) {
    val session = LocalSession.current
    val roomSummaries by session!!.roomService().getRoomSummariesLive(
        roomSummaryQueryParams {
            this.memberships = listOf(Membership.JOIN)
            this.includeType = listOf(TwoMMatrix.ROOM_TYPE)
        }
    ).observeAsState()

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