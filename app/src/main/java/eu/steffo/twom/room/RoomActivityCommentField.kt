package eu.steffo.twom.room

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import eu.steffo.twom.R
import eu.steffo.twom.theme.colorRoleLater
import eu.steffo.twom.theme.colorRoleMaybe
import eu.steffo.twom.theme.colorRoleNoway
import eu.steffo.twom.theme.colorRoleSure
import eu.steffo.twom.theme.colorRoleUnknown

@Composable
@Preview
fun RoomActivityCommentField(
    modifier: Modifier = Modifier,
    value: String = "",
    onValueChange: (value: String) -> Unit = {},
    rsvpAnswer: RSVPAnswer? = null,
) {
    val colorRole = when (rsvpAnswer) {
        RSVPAnswer.SURE -> colorRoleSure()
        RSVPAnswer.LATER -> colorRoleLater()
        RSVPAnswer.MAYBE -> colorRoleMaybe()
        RSVPAnswer.NOWAY -> colorRoleNoway()
        null -> colorRoleUnknown()
    }

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        shape = MaterialTheme.shapes.small,
        placeholder = {
            Text(
                text = when (rsvpAnswer) {
                    RSVPAnswer.SURE -> stringResource(R.string.room_rsvp_sure_placeholder)
                    RSVPAnswer.LATER -> stringResource(R.string.room_rsvp_later_placeholder)
                    RSVPAnswer.MAYBE -> stringResource(R.string.room_rsvp_maybe_placeholder)
                    RSVPAnswer.NOWAY -> stringResource(R.string.room_rsvp_noway_placeholder)
                    null -> stringResource(R.string.room_rsvp_unknown_placeholder)
                }
            )
        },
        colors = if (rsvpAnswer != null) {
            OutlinedTextFieldDefaults.colors(
                focusedContainerColor = colorRole.valueContainer,
                unfocusedContainerColor = colorRole.valueContainer,
                focusedTextColor = colorRole.onValueContainer,
                unfocusedTextColor = colorRole.onValueContainer,
                focusedBorderColor = colorRole.onValueContainer,
                unfocusedBorderColor = colorRole.onValueContainer.copy(alpha = 0.3f),
                cursorColor = colorRole.onValueContainer,
            )
        } else {
            OutlinedTextFieldDefaults.colors(
                focusedBorderColor = LocalContentColor.current,
                unfocusedBorderColor = MaterialTheme.colorScheme.surfaceVariant,
            )
        },
    )
}
