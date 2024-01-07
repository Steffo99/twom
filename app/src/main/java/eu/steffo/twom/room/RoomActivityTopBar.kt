package eu.steffo.twom.room

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import eu.steffo.twom.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoomActivityTopBar(
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
) {
    val isLoading = (LocalRoom.current == null)
    val roomSummary = LocalRoom.current?.getOrNull()
    val isError = (!isLoading && roomSummary == null)

    TopAppBar(
        modifier = modifier,
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = LocalContext.current.getString(R.string.back)
                )
            }
        },
        title = {
            if (roomSummary != null) {
                Text(
                    text = roomSummary.displayName,
                    style = MaterialTheme.typography.titleLarge,
                )
            }
        },
        actions = {
            if (isLoading) {
                CircularProgressIndicator()
            } else if (isError) {
                Icon(Icons.Filled.Warning, stringResource(R.string.error))
            } else {
                RoomActivityRoomIconButton(
                    avatarUrl = roomSummary!!.avatarUrl,
                )
            }
        },
    )
}