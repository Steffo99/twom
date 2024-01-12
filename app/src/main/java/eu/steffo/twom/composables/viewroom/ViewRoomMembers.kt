package eu.steffo.twom.composables.viewroom

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import eu.steffo.twom.R
import eu.steffo.twom.composables.errorhandling.ErrorText
import eu.steffo.twom.composables.theme.basePadding
import org.matrix.android.sdk.api.session.room.members.RoomMemberQueryParams
import kotlin.jvm.optionals.getOrNull

@Composable
fun ViewRoomMembers() {
    val roomRequest = LocalRoom.current
    if (roomRequest == null) {
        ErrorText(stringResource(R.string.room_error_room_missing))
        return
    }

    val room = roomRequest.getOrNull()
    if (room == null) {
        ErrorText(stringResource(R.string.room_error_room_notfound))
        return
    }

    val roomMembers by room.membershipService().getRoomMembersLive(
        RoomMemberQueryParams.Builder().build()
    ).observeAsState()
    if (roomMembers == null) {
        ErrorText(stringResource(R.string.room_error_members_notfound))
        return
    }

    Row(Modifier.basePadding()) {
        Text(
            text = stringResource(R.string.room_invitees_title),
            style = MaterialTheme.typography.labelLarge,
        )
    }
    roomMembers!!.forEach {
        MemberListItem(member = it)
    }
}