package eu.steffo.twom.composables.viewroom

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import eu.steffo.twom.R
import eu.steffo.twom.composables.errorhandling.ErrorText
import eu.steffo.twom.composables.matrix.LocalSession


@Composable
fun ViewRoomContent(
    modifier: Modifier = Modifier,
) {
    val session = LocalSession.current
    if (session == null) {
        ErrorText(
            text = stringResource(R.string.error_session_missing)
        )
        return
    }

    Box(
        modifier = modifier
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier.fillMaxHeight()
        ) {
            ViewRoomTopic()
            ViewRoomForm()
            ViewRoomMembers()
        }
    }
}
