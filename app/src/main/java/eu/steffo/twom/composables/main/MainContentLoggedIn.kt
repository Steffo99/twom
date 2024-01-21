package eu.steffo.twom.composables.main

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import eu.steffo.twom.R
import eu.steffo.twom.composables.errorhandling.ErrorText
import eu.steffo.twom.composables.matrix.LocalSession
import eu.steffo.twom.composables.theme.basePadding
import eu.steffo.twom.utils.TwoMGlobals
import org.matrix.android.sdk.api.session.room.model.Membership
import org.matrix.android.sdk.api.session.room.roomSummaryQueryParams

@Composable
fun MainContentLoggedIn(
    modifier: Modifier = Modifier,
) {
    val session = LocalSession.current
    if (session == null) {
        ErrorText(
            text = stringResource(R.string.error_session_missing)
        )
        return
    }

    val roomSummaries by session.roomService().getRoomSummariesLive(
        roomSummaryQueryParams {
            this.memberships = listOf(Membership.JOIN, Membership.INVITE)
            this.includeType = listOf(TwoMGlobals.ROOM_TYPE)
        }
    ).observeAsState()

    Column(modifier) {
        if (roomSummaries == null) {
            Text(
                modifier = Modifier.basePadding(),
                text = stringResource(R.string.loading)
            )
        } else if (roomSummaries!!.isEmpty()) {
            Text(
                modifier = Modifier.basePadding(),
                text = stringResource(R.string.main_roomlist_empty_text)
            )
        } else {
            roomSummaries!!.forEach { RoomListItem(it) }
        }
    }
}