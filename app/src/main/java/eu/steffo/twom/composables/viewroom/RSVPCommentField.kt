package eu.steffo.twom.composables.viewroom

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import eu.steffo.twom.utils.RSVPAnswer

@Composable
@Preview
fun RSVPCommentField(
    modifier: Modifier = Modifier,
    value: String = "",
    onChange: (value: String) -> Unit = {},
    currentAnswer: RSVPAnswer = RSVPAnswer.UNKNOWN,
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onChange,
        singleLine = true,
        shape = MaterialTheme.shapes.small,
        placeholder = {
            Text(currentAnswer.toCommentPlaceholder())
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = currentAnswer.staticColorRole.containerColor(),
            unfocusedContainerColor = currentAnswer.staticColorRole.containerColor(),
            focusedTextColor = currentAnswer.staticColorRole.onContainerColor(),
            unfocusedTextColor = currentAnswer.staticColorRole.onContainerColor(),
            focusedBorderColor = currentAnswer.staticColorRole.onContainerColor(),
            unfocusedBorderColor = currentAnswer.staticColorRole.onContainerColor()
                .copy(alpha = 0.3f),
            cursorColor = currentAnswer.staticColorRole.onContainerColor(),
        )
    )
}
