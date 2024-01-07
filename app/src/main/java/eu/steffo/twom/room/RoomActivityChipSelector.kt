package eu.steffo.twom.room

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import eu.steffo.twom.theme.TwoMPadding

@Composable
@Preview
fun RoomActivityChipSelector(
    modifier: Modifier = Modifier,
    value: RSVPAnswer? = null,
    onChange: (answer: RSVPAnswer?) -> Unit = {},
) {
    Box(
        modifier = modifier
            .horizontalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier
                .padding(start = 8.dp, end = 8.dp)
        ) {
            RoomActivityChipSure(
                modifier = TwoMPadding.chips,
                selected = (value == RSVPAnswer.SURE),
                onClick = {
                    onChange(
                        when (value) {
                            RSVPAnswer.SURE -> null
                            else -> RSVPAnswer.SURE
                        }
                    )
                }
            )
            RoomActivityChipLater(
                modifier = TwoMPadding.chips,
                selected = (value == RSVPAnswer.LATER),
                onClick = {
                    onChange(
                        when (value) {
                            RSVPAnswer.LATER -> null
                            else -> RSVPAnswer.LATER
                        }
                    )
                }
            )
            RoomActivityChipMaybe(
                modifier = TwoMPadding.chips,
                selected = (value == RSVPAnswer.MAYBE),
                onClick = {
                    onChange(
                        when (value) {
                            RSVPAnswer.MAYBE -> null
                            else -> RSVPAnswer.MAYBE
                        }
                    )
                }
            )
            RoomActivityChipNoway(
                modifier = TwoMPadding.chips,
                selected = (value == RSVPAnswer.NOWAY),
                onClick = {
                    onChange(
                        when (value) {
                            RSVPAnswer.NOWAY -> null
                            else -> RSVPAnswer.NOWAY
                        }
                    )
                }
            )
        }
    }
}