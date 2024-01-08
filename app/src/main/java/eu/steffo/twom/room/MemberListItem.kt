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

    val icon = rsvpAnswer.toIcon()
    val responseResourceId = rsvpAnswer.toResponseResourceId()
    val colorRole = rsvpAnswer.toStaticColorRole()

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
                imageVector = icon,
                contentDescription = stringResource(responseResourceId),
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