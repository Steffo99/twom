package eu.steffo.twom.composables.viewroom

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import eu.steffo.twom.composables.errorhandling.LoadingText
import eu.steffo.twom.composables.theme.basePadding
import eu.steffo.twom.utils.RSVP
import eu.steffo.twom.utils.RSVPAnswer

@Composable
fun RSVPForm(
    published: RSVP,
    onRequestPublish: (newAnswer: RSVPAnswer, newComment: String) -> Unit = { _, _ -> },
    isPublishRunning: Boolean = false,
) {
    if (published.answer == RSVPAnswer.LOADING) {
        Row(Modifier.basePadding()) {
            LoadingText()
        }
        return
    }

    var currentAnswer by rememberSaveable { mutableStateOf(published.answer) }
    var currentComment by rememberSaveable { mutableStateOf(published.comment) }

    val hasChanged = (currentAnswer != published.answer || currentComment != published.comment)

    RSVPChipRow(
        value = currentAnswer,
        onChange = { currentAnswer = it },
    )
    RSVPCommentField(
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        value = currentComment,
        onChange = { currentComment = it },
        currentAnswer = currentAnswer,
    )
    RSVPUpdateButton(
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp, top = 4.dp, bottom = 4.dp)
            .fillMaxWidth(),
        onClick = { onRequestPublish(currentAnswer, currentComment) },
        enabled = hasChanged && !isPublishRunning,
        currentAnswer = currentAnswer,
    )
}
