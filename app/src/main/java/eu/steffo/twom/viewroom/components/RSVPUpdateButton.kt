package eu.steffo.twom.viewroom.components

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import eu.steffo.twom.R
import eu.steffo.twom.viewroom.utils.RSVPAnswer

@Composable
@Preview
fun RSVPUpdateButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit = {},
    currentAnswer: RSVPAnswer = RSVPAnswer.UNKNOWN,
) {
    Button(
        modifier = modifier,
        enabled = enabled,
        onClick = onClick,
        shape = MaterialTheme.shapes.small,
        colors = ButtonDefaults.buttonColors(
            containerColor = currentAnswer.staticColorRole.color(),
            contentColor = currentAnswer.staticColorRole.onColor(),
        )
    ) {
        Text(
            text = stringResource(R.string.room_rsvp_update_label)
        )
    }
}
