package eu.steffo.twom.composables.viewroom

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import eu.steffo.twom.R
import eu.steffo.twom.composables.errorhandling.ErrorIconButton
import eu.steffo.twom.composables.errorhandling.LocalizableError
import eu.steffo.twom.composables.navigation.BackIconButton


@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun ViewRoomTopBar(
    modifier: Modifier = Modifier,
    roomName: String? = null,
    roomAvatarUrl: String? = null,
    isLoading: Boolean = false,
    error: LocalizableError? = null,
) {
    TopAppBar(
        modifier = modifier,
        navigationIcon = {
            BackIconButton()
        },
        title = {
            if (roomName != null) {
                Text(
                    text = roomName,
                    style = MaterialTheme.typography.titleLarge,
                )
            } else {
                Text(
                    text = stringResource(R.string.loading),
                    style = MaterialTheme.typography.titleLarge,
                    color = LocalContentColor.current.copy(0.4f)
                )
            }
        },
        actions = {
            if (isLoading) {
                CircularProgressIndicator()
            } else if (error != null && error.occurred()) {
                ErrorIconButton(
                    message = error.renderString()!!
                )
            } else {
                RoomIconButton(
                    avatarUrl = roomAvatarUrl,
                )
            }
        },
    )
}