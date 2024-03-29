package eu.steffo.twom.viewroom.effects

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import eu.steffo.twom.matrix.utils.TwoMMatrix
import eu.steffo.twom.viewroom.utils.RSVP
import eu.steffo.twom.viewroom.utils.RSVPAnswer
import org.matrix.android.sdk.api.query.QueryStringValue
import org.matrix.android.sdk.api.session.room.Room
import org.matrix.android.sdk.api.session.room.model.Membership
import org.matrix.android.sdk.api.session.room.model.RoomMemberSummary

@Composable
fun observeRSVP(room: Room, member: RoomMemberSummary): RSVP? {
    if (member.membership == Membership.INVITE) {
        return RSVP(
            event = null,
            answer = RSVPAnswer.PENDING,
            comment = "",
        )
    }

    if (member.membership == Membership.LEAVE || member.membership == Membership.BAN) {
        return null
    }

    val request by room.stateService().getStateEventLive(
        eventType = TwoMMatrix.RSVP_STATE_TYPE,
        stateKey = QueryStringValue.Equals(member.userId),
    ).observeAsState()

    if (request == null) {
        return RSVP(
            event = null,
            answer = RSVPAnswer.LOADING,
            comment = "",
        )
    }

    val event = request!!.getOrNull()
        ?: return RSVP(
            event = null,
            answer = RSVPAnswer.NONE,
            comment = "",
        )

    val content = event.content
        ?: return RSVP(
            event = event,
            answer = RSVPAnswer.UNKNOWN,
            comment = "",
        )

    // Redactions
    if (content.isEmpty()) {
        return RSVP(
            event = event,
            answer = RSVPAnswer.NONE,
            comment = "",
        )
    }

    val commentField = content[TwoMMatrix.RSVP_STATE_COMMENT_FIELD]
        ?: return RSVP(
            event = event,
            answer = RSVPAnswer.UNKNOWN,
            comment = "",
        )

    val comment = commentField as? String
        ?: return RSVP(
            event = event,
            answer = RSVPAnswer.UNKNOWN,
            comment = "",
        )

    val answerField = content[TwoMMatrix.RSVP_STATE_ANSWER_FIELD]
        ?: return RSVP(
            event = event,
            answer = RSVPAnswer.NONE,
            comment = comment,
        )

    val answerString = answerField as? String
        ?: return RSVP(
            event = event,
            answer = RSVPAnswer.UNKNOWN,
            comment = comment,
        )

    val answer = when (answerString) {
        RSVPAnswer.SURE.value -> RSVPAnswer.SURE
        RSVPAnswer.LATER.value -> RSVPAnswer.LATER
        RSVPAnswer.MAYBE.value -> RSVPAnswer.MAYBE
        RSVPAnswer.NOWAY.value -> RSVPAnswer.NOWAY
        RSVPAnswer.NONE.value -> RSVPAnswer.NONE
        else -> RSVPAnswer.UNKNOWN
    }

    return RSVP(
        event = event,
        answer = answer,
        comment = comment,
    )
}