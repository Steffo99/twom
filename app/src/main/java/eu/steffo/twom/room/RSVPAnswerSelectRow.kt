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
fun RSVPAnswerSelectRow(
    modifier: Modifier = Modifier,
    value: RSVPAnswer? = null,
    onChange: (answer: RSVPAnswer?) -> Unit = {},
) {
    fun toggleSwitch(representing: RSVPAnswer): () -> Unit {
        return {
            onChange(
                when (value) {
                    representing -> null
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
                .padding(start = 10.dp, end = 10.dp)
        ) {
            RSVPAnswerFilterChip(
                modifier = TwoMPadding.chips,
                representing = RSVPAnswer.SURE,
                selected = (value == RSVPAnswer.SURE),
                onClick = toggleSwitch(RSVPAnswer.SURE)
            )
            RSVPAnswerFilterChip(
                modifier = TwoMPadding.chips,
                representing = RSVPAnswer.LATER,
                selected = (value == RSVPAnswer.LATER),
                onClick = toggleSwitch(RSVPAnswer.LATER)
            )
            RSVPAnswerFilterChip(
                modifier = TwoMPadding.chips,
                representing = RSVPAnswer.MAYBE,
                selected = (value == RSVPAnswer.MAYBE),
                onClick = toggleSwitch(RSVPAnswer.MAYBE)
            )
            RSVPAnswerFilterChip(
                modifier = TwoMPadding.chips,
                representing = RSVPAnswer.NOWAY,
                selected = (value == RSVPAnswer.NOWAY),
                onClick = toggleSwitch(RSVPAnswer.NOWAY)
            )
        }
    }
}