package eu.steffo.twom.main

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import eu.steffo.twom.R
import eu.steffo.twom.matrix.LocalSession
import eu.steffo.twom.matrix.TwoMMatrix
import eu.steffo.twom.theme.TwoMPadding
import kotlinx.coroutines.launch
import org.matrix.android.sdk.api.session.room.model.Membership
import org.matrix.android.sdk.api.session.room.roomSummaryQueryParams

@Composable
fun MainActivityRoomList(
    modifier: Modifier = Modifier,
    onClickRoom: (roomId: String) -> Unit = {},
) {
    val scope = rememberCoroutineScope()

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
                    roomSummary = it,
                    onClickRoom = onClickRoom,
                    onLeaveRoom = {
                        scope.launch LeaveRoom@{
                            Log.i("Main", "Leaving room `$it`...")
                            try {
                                session!!.roomService().leaveRoom(it, "Decided to leave the room")
                            } catch (error: Throwable) {
                                Log.e("Main", "Failed to leave room `$it`: $error")
                                // TODO: Display an error somewhere
                                return@LeaveRoom
                            }
                            Log.d("Main", "Successfully left room `$it`!")
                        }
                    }
                )
            }
        }
    }
}