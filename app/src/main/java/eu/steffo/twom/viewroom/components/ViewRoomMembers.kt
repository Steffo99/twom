package eu.steffo.twom.viewroom.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import eu.steffo.twom.R
import eu.steffo.twom.errorhandling.components.ErrorText
import eu.steffo.twom.errorhandling.components.LoadingText
import eu.steffo.twom.theme.utils.basePadding
import eu.steffo.twom.viewroom.complocals.LocalRoom
import org.matrix.android.sdk.api.session.room.members.RoomMemberQueryParams
import kotlin.jvm.optionals.getOrNull

@Composable
fun ViewRoomMembers() {
    Row(Modifier.basePadding()) {
        Text(
            text = stringResource(R.string.room_invitees_title),
            style = MaterialTheme.typography.labelLarge,
        )
    }

    val roomRequest = LocalRoom.current
    if (roomRequest == null) {
        Row(Modifier.basePadding()) {
            LoadingText()
        }
        return
    }

    val room = roomRequest.getOrNull()
    if (room == null) {
        Row(Modifier.basePadding()) {
            ErrorText(
                text = stringResource(R.string.invite_error_room_notfound)
            )
        }
        return
    }

    val roomMembersRequest by room.membershipService().getRoomMembersLive(
        RoomMemberQueryParams.Builder().build()
    ).observeAsState()
    if (roomMembersRequest == null) {
        Row(Modifier.basePadding()) {
            LoadingText()
        }
        return
    }

    roomMembersRequest!!.forEach {
        MemberListItem(member = it)
    }
}