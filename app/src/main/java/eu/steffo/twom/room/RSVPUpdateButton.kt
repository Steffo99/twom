package eu.steffo.twom.room

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import eu.steffo.twom.R

@Composable
@Preview
fun RSVPUpdateButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit = {},
    currentRsvpAnswer: RSVPAnswer? = null,
) {
    val colorRole = currentRsvpAnswer.toStaticColorRole()

    Button(
        modifier = modifier,
        enabled = enabled,
        onClick = onClick,
        shape = MaterialTheme.shapes.small,
        colors = ButtonDefaults.buttonColors(
            containerColor = colorRole.value,
            contentColor = colorRole.onValue,
        )
    ) {
        Text(
            text = stringResource(R.string.room_update_label)
        )
    }
}
