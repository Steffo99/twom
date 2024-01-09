package eu.steffo.twom.room

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material.icons.outlined.Help
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.vector.ImageVector
import eu.steffo.twom.R
import eu.steffo.twom.theme.StaticColorRole
import eu.steffo.twom.theme.colorRoleLater
import eu.steffo.twom.theme.colorRoleMaybe
import eu.steffo.twom.theme.colorRoleNoway
import eu.steffo.twom.theme.colorRoleSure
import eu.steffo.twom.theme.colorRoleUnknown
import org.matrix.android.sdk.api.query.QueryStringValue
import org.matrix.android.sdk.api.session.events.model.Event
import org.matrix.android.sdk.api.session.room.Room
import org.matrix.android.sdk.api.util.Optional

enum class RSVPAnswer {
    SURE,
    LATER,
    MAYBE,
    NOWAY,
    UNKNOWN,
}

@Composable
fun RSVPAnswer.toStaticColorRole(): StaticColorRole {
    return when (this) {
        RSVPAnswer.SURE -> colorRoleSure()
        RSVPAnswer.LATER -> colorRoleLater()
        RSVPAnswer.MAYBE -> colorRoleMaybe()
        RSVPAnswer.NOWAY -> colorRoleNoway()
        RSVPAnswer.UNKNOWN -> colorRoleUnknown()
    }
}

fun RSVPAnswer.toIcon(): ImageVector {
    return when (this) {
        RSVPAnswer.SURE -> Icons.Outlined.CheckCircle
        RSVPAnswer.LATER -> Icons.Outlined.Schedule
        RSVPAnswer.MAYBE -> Icons.Outlined.Help
        RSVPAnswer.NOWAY -> Icons.Outlined.Cancel
        RSVPAnswer.UNKNOWN -> Icons.Outlined.Circle
    }
}

fun RSVPAnswer.toLabelResourceId(): Int {
    return when (this) {
        RSVPAnswer.SURE -> R.string.room_rsvp_sure_label
        RSVPAnswer.LATER -> R.string.room_rsvp_later_label
        RSVPAnswer.MAYBE -> R.string.room_rsvp_maybe_label
        RSVPAnswer.NOWAY -> R.string.room_rsvp_noway_label
        RSVPAnswer.UNKNOWN -> R.string.room_rsvp_unknown_label
    }
}

fun RSVPAnswer.toResponseResourceId(): Int {
    return when (this) {
        RSVPAnswer.SURE -> R.string.room_rsvp_sure_response
        RSVPAnswer.LATER -> R.string.room_rsvp_later_response
        RSVPAnswer.MAYBE -> R.string.room_rsvp_maybe_response
        RSVPAnswer.NOWAY -> R.string.room_rsvp_noway_response
        RSVPAnswer.UNKNOWN -> R.string.room_rsvp_unknown_response
    }
}

fun RSVPAnswer.toPlaceholderResourceId(): Int {
    return when (this) {
        RSVPAnswer.SURE -> R.string.room_rsvp_sure_placeholder
        RSVPAnswer.LATER -> R.string.room_rsvp_later_placeholder
        RSVPAnswer.MAYBE -> R.string.room_rsvp_maybe_placeholder
        RSVPAnswer.NOWAY -> R.string.room_rsvp_noway_placeholder
        RSVPAnswer.UNKNOWN -> R.string.room_rsvp_unknown_placeholder
    }
}

fun makeRSVP(request: State<Optional<Event>?>?): Triple<Event, RSVPAnswer, String>? {
    val event = request?.value?.getOrNull() ?: return null
    val content = event.content ?: return null

    val answerAny = content["answer"]
    val commentAny = content["comment"]

    val answer = if (answerAny is String) {
        try {
            RSVPAnswer.valueOf(answerAny)
        } catch (_: IllegalArgumentException) {
            RSVPAnswer.UNKNOWN
        }
    } else {
        RSVPAnswer.UNKNOWN
    }

    val comment = if (commentAny is String) {
        commentAny
    } else {
        ""
    }

    return Triple(event, answer, comment)
}

@Composable
fun observeRsvpAsLiveState(room: Room, userId: String): Triple<Event, RSVPAnswer, String>? {
    val stateRequest = room.stateService().getStateEventLive(
        eventType = "eu.steffo.twom.rsvp",
        stateKey = QueryStringValue.Equals(userId),
    ).observeAsState()

    return makeRSVP(stateRequest)
}
