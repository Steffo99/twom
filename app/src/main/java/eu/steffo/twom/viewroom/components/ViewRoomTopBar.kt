package eu.steffo.twom.viewroom.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import eu.steffo.twom.errorhandling.components.ErrorText
import eu.steffo.twom.errorhandling.components.LoadingText
import eu.steffo.twom.navigation.components.BackIconButton
import eu.steffo.twom.viewroom.complocals.LocalRoomSummary


@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun ViewRoomTopBar(
    modifier: Modifier = Modifier,
) {
    val roomSummaryRequest = LocalRoomSummary.current
    val roomSummary = roomSummaryRequest?.getOrNull()

    TopAppBar(
        modifier = modifier,
        navigationIcon = {
            BackIconButton()
        },
        title = {
            if (roomSummaryRequest == null) {
                LoadingText(
                    style = MaterialTheme.typography.titleLarge,
                )
            } else if (roomSummary == null) {
                ErrorText(
                    style = MaterialTheme.typography.titleLarge,
                )
            } else {
                Text(
                    text = roomSummary.displayName,
                    style = MaterialTheme.typography.titleLarge,
                )
            }
        },
        actions = {
            RoomIconButton()
        },
    )
}
