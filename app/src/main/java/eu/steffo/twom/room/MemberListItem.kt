package eu.steffo.twom.room

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import eu.steffo.twom.R
import eu.steffo.twom.matrix.LocalSession
import eu.steffo.twom.matrix.avatar.AvatarFromURL
import eu.steffo.twom.theme.colorRoleLater
import eu.steffo.twom.theme.colorRoleMaybe
import eu.steffo.twom.theme.colorRoleNoway
import eu.steffo.twom.theme.colorRoleSure
import eu.steffo.twom.theme.colorRoleUnknown
import eu.steffo.twom.theme.iconRoleLater
import eu.steffo.twom.theme.iconRoleMaybe
import eu.steffo.twom.theme.iconRoleNoway
import eu.steffo.twom.theme.iconRoleSure
import eu.steffo.twom.theme.iconRoleUnknown
import org.matrix.android.sdk.api.session.getUser

// TODO: Check this with brain on

@Composable
fun MemberListItem(
    modifier: Modifier = Modifier,
    memberId: String,
    onClickMember: (memberId: String) -> Unit = {},
    rsvpAnswer: RSVPAnswer? = null,
    rsvpComment: String = "",
) {
    val session = LocalSession.current

    val user = session?.getUser(memberId)

    val colorRole = when (rsvpAnswer) {
        RSVPAnswer.SURE -> colorRoleSure()
        RSVPAnswer.LATER -> colorRoleLater()
        RSVPAnswer.MAYBE -> colorRoleMaybe()
        RSVPAnswer.NOWAY -> colorRoleNoway()
        null -> colorRoleUnknown()
    }
    val iconRole = when (rsvpAnswer) {
        RSVPAnswer.SURE -> iconRoleSure
        RSVPAnswer.LATER -> iconRoleLater
        RSVPAnswer.MAYBE -> iconRoleMaybe
        RSVPAnswer.NOWAY -> iconRoleNoway
        null -> iconRoleUnknown
    }
    val iconDescription = when (rsvpAnswer) {
        RSVPAnswer.SURE -> stringResource(R.string.room_rsvp_sure_response)
        RSVPAnswer.LATER -> stringResource(R.string.room_rsvp_later_response)
        RSVPAnswer.MAYBE -> stringResource(R.string.room_rsvp_maybe_response)
        RSVPAnswer.NOWAY -> stringResource(R.string.room_rsvp_noway_response)
        null -> stringResource(R.string.room_rsvp_unknown_response)
    }

    ListItem(
        modifier = modifier.clickable {
            onClickMember(memberId)
        },
        headlineContent = {
            Text(
                text = user?.displayName ?: stringResource(R.string.user_unresolved_name),
            )
        },
        leadingContent = {
            Box(
                Modifier
                    .padding(end = 10.dp)
                    .size(40.dp)
                    .clip(MaterialTheme.shapes.extraLarge)
            ) {
                AvatarFromURL(
                    url = user?.avatarUrl,
                )
            }
        },
        trailingContent = {
            Icon(
                imageVector = iconRole,
                contentDescription = iconDescription,
                tint = colorRole.value,
            )
        },
        supportingContent = {
            if (rsvpComment != "") {
                Text(
                    text = rsvpComment,
                    color = colorRole.value,
                )
            }
        },
    )
}