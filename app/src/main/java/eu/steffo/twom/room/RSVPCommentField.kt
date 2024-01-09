package eu.steffo.twom.room

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun RSVPCommentField(
    modifier: Modifier = Modifier,
    value: String = "",
    onChange: (value: String) -> Unit = {},
    rsvpAnswer: RSVPAnswer = RSVPAnswer.UNKNOWN,
) {
    val colorRole = rsvpAnswer.toStaticColorRole()

    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onChange,
        singleLine = true,
        shape = MaterialTheme.shapes.small,
        placeholder = {
            Text(
                text = stringResource(rsvpAnswer.toPlaceholderResourceId())
            )
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = colorRole.valueContainer,
            unfocusedContainerColor = colorRole.valueContainer,
            focusedTextColor = colorRole.onValueContainer,
            unfocusedTextColor = colorRole.onValueContainer,
            focusedBorderColor = colorRole.onValueContainer,
            unfocusedBorderColor = colorRole.onValueContainer.copy(alpha = 0.3f),
            cursorColor = colorRole.onValueContainer,
        )
    )
}
