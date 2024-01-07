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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import eu.steffo.twom.R
import eu.steffo.twom.matrix.LocalSession
import eu.steffo.twom.matrix.avatar.AvatarFromURL
import eu.steffo.twom.theme.colorRoleLater
import eu.steffo.twom.theme.colorRoleMaybe
import eu.steffo.twom.theme.colorRoleNoway
import eu.steffo.twom.theme.colorRoleSure
import eu.steffo.twom.theme.iconRoleLater
import eu.steffo.twom.theme.iconRoleMaybe
import eu.steffo.twom.theme.iconRoleNoway
import eu.steffo.twom.theme.iconRoleSure
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

    // TODO: These are going to get cached many times...
    val crSure = colorRoleSure()
    val crLater = colorRoleLater()
    val crMaybe = colorRoleMaybe()
    val crNoway = colorRoleNoway()

    ListItem(
        modifier = Modifier.clickable {
            onClickMember(memberId)
        },
        headlineContent = {
            Text(user?.displayName ?: stringResource(R.string.user_unresolved_name))
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
            when (rsvpAnswer) {
                RSVPAnswer.SURE -> {
                    Icon(
                        imageVector = iconRoleSure,
                        contentDescription = stringResource(R.string.room_rsvp_sure_label),
                        tint = crSure.value,
                    )
                }

                RSVPAnswer.LATER -> {
                    Icon(
                        imageVector = iconRoleLater,
                        contentDescription = stringResource(R.string.room_rsvp_later_label),
                        tint = crLater.value,
                    )
                }

                RSVPAnswer.MAYBE -> {
                    Icon(
                        imageVector = iconRoleMaybe,
                        contentDescription = stringResource(R.string.room_rsvp_maybe_label),
                        tint = crMaybe.value,
                    )
                }

                RSVPAnswer.NOWAY -> {
                    Icon(
                        imageVector = iconRoleNoway,
                        contentDescription = stringResource(R.string.room_rsvp_later_label),
                        tint = crNoway.value,
                    )
                }

                null -> {}
            }
        },
        supportingContent = {
            if (rsvpComment != "") {
                Text(
                    text = rsvpComment,
                    color = when (rsvpAnswer) {
                        RSVPAnswer.SURE -> crSure.value
                        RSVPAnswer.LATER -> crLater.value
                        RSVPAnswer.MAYBE -> crMaybe.value
                        RSVPAnswer.NOWAY -> crNoway.value
                        null -> Color.Unspecified
                    }
                )
            }
        },
    )
}