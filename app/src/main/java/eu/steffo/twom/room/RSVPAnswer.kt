package eu.steffo.twom.room

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import eu.steffo.twom.R
import eu.steffo.twom.theme.StaticColorRole
import eu.steffo.twom.theme.colorRoleLater
import eu.steffo.twom.theme.colorRoleMaybe
import eu.steffo.twom.theme.colorRoleNoway
import eu.steffo.twom.theme.colorRoleSure
import eu.steffo.twom.theme.colorRoleUnknown
import eu.steffo.twom.theme.iconLater
import eu.steffo.twom.theme.iconMaybe
import eu.steffo.twom.theme.iconNoway
import eu.steffo.twom.theme.iconSure
import eu.steffo.twom.theme.iconUnknown

enum class RSVPAnswer {
    SURE,
    LATER,
    MAYBE,
    NOWAY,
}

@Composable
fun RSVPAnswer?.toStaticColorRole(): StaticColorRole {
    return when (this) {
        RSVPAnswer.SURE -> colorRoleSure()
        RSVPAnswer.LATER -> colorRoleLater()
        RSVPAnswer.MAYBE -> colorRoleMaybe()
        RSVPAnswer.NOWAY -> colorRoleNoway()
        null -> colorRoleUnknown()
    }
}

fun RSVPAnswer?.toIcon(): ImageVector {
    return when (this) {
        RSVPAnswer.SURE -> iconSure
        RSVPAnswer.LATER -> iconLater
        RSVPAnswer.MAYBE -> iconMaybe
        RSVPAnswer.NOWAY -> iconNoway
        null -> iconUnknown
    }
}

fun RSVPAnswer.toLabelResourceId(): Int {
    return when (this) {
        RSVPAnswer.SURE -> R.string.room_rsvp_sure_label
        RSVPAnswer.LATER -> R.string.room_rsvp_later_label
        RSVPAnswer.MAYBE -> R.string.room_rsvp_maybe_label
        RSVPAnswer.NOWAY -> R.string.room_rsvp_noway_label
    }
}

fun RSVPAnswer?.toResponseResourceId(): Int {
    return when (this) {
        RSVPAnswer.SURE -> R.string.room_rsvp_sure_response
        RSVPAnswer.LATER -> R.string.room_rsvp_later_response
        RSVPAnswer.MAYBE -> R.string.room_rsvp_maybe_response
        RSVPAnswer.NOWAY -> R.string.room_rsvp_noway_response
        null -> R.string.room_rsvp_unknown_response
    }
}

fun RSVPAnswer?.toPlaceholderResourceId(): Int {
    return when (this) {
        RSVPAnswer.SURE -> R.string.room_rsvp_sure_placeholder
        RSVPAnswer.LATER -> R.string.room_rsvp_later_placeholder
        RSVPAnswer.MAYBE -> R.string.room_rsvp_maybe_placeholder
        RSVPAnswer.NOWAY -> R.string.room_rsvp_noway_placeholder
        null -> R.string.room_rsvp_unknown_placeholder
    }
}
