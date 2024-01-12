package eu.steffo.twom.composables.viewroom

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import eu.steffo.twom.composables.theme.chipPadding
import eu.steffo.twom.utils.RSVPAnswer

@Composable
@Preview
fun RSVPChipRow(
    modifier: Modifier = Modifier,
    value: RSVPAnswer = RSVPAnswer.UNKNOWN,
    onChange: (answer: RSVPAnswer) -> Unit = {},
) {
    fun toggleSwitch(representing: RSVPAnswer): () -> Unit {
        return {
            onChange(
                when (value) {
                    representing -> RSVPAnswer.UNKNOWN
                    else -> representing
                }
            )
        }
    }

    Box(
        modifier = modifier
            .horizontalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier
                .padding(start = 8.dp, end = 8.dp)
        ) {
            RSVPChip(
                modifier = Modifier.chipPadding(),
                representedAnswer = RSVPAnswer.SURE,
                selected = (value == RSVPAnswer.SURE),
                onClick = toggleSwitch(RSVPAnswer.SURE)
            )
            RSVPChip(
                modifier = Modifier.chipPadding(),
                representedAnswer = RSVPAnswer.LATER,
                selected = (value == RSVPAnswer.LATER),
                onClick = toggleSwitch(RSVPAnswer.LATER)
            )
            RSVPChip(
                modifier = Modifier.chipPadding(),
                representedAnswer = RSVPAnswer.MAYBE,
                selected = (value == RSVPAnswer.MAYBE),
                onClick = toggleSwitch(RSVPAnswer.MAYBE)
            )
            RSVPChip(
                modifier = Modifier.chipPadding(),
                representedAnswer = RSVPAnswer.NOWAY,
                selected = (value == RSVPAnswer.NOWAY),
                onClick = toggleSwitch(RSVPAnswer.NOWAY)
            )
        }
    }
}