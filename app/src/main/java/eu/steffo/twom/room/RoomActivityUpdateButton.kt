import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import eu.steffo.twom.R
import eu.steffo.twom.room.RSVPAnswer

@Composable
@Preview
fun RoomActivityUpdateButton(
    onClick: () -> Unit = {},
    rsvpAnswer: RSVPAnswer? = null,
) {
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick,
        shape = MaterialTheme.shapes.small,
    ) {
        Text(
            text = stringResource(R.string.room_update_label)
        )
    }
}