package eu.steffo.twom.main

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Divider
import androidx.compose.material3.ListItem
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
import eu.steffo.twom.matrix.Avatar
import eu.steffo.twom.theme.TwoMPadding
import org.matrix.android.sdk.api.session.Session
import org.matrix.android.sdk.api.session.room.model.RoomSummary
import org.matrix.android.sdk.api.session.room.roomSummaryQueryParams

@Composable
fun MainActivityRoomListControl(
    session: Session,
    modifier: Modifier = Modifier,
) {
    var roomSummaries by remember { mutableStateOf<List<RoomSummary>?>(null) }

    LaunchedEffect(session) GetRoomSummaries@{
        Log.d("RoomList", "Getting room summaries...")
        val queryParamsBuilder = roomSummaryQueryParams()
        roomSummaries = session.roomService().getRoomSummaries(queryParamsBuilder)
        Log.d("RoomList", "Obtained room summaries: $roomSummaries")

        val sanityCheck = session.roomService().getRoom("!MdYm4p7umKo4DYX71m:candy.steffo.eu")
        Log.d("RoomList", "Sanity check: $sanityCheck")
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
                text = stringResource(R.string.room_list_empty_text)
            )
        } else {
            roomSummaries!!.forEach {
                ListItem(
                    headlineContent = {
                        Text(it.name)
                    },
                    leadingContent = {
                        Avatar(
                            session = session,
                            url = it.avatarUrl,
                            contentDescription = ""  // TODO: Is this correct?
                        )
                    }
                )
                Divider()
            }
        }
    }
}