package eu.steffo.twom.ui.input

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import eu.steffo.twom.R


enum class HomeserverFieldState {
    Empty,
    Waiting,
    Validating,
    Error,
    Done,
}


@Composable
@Preview
fun HomeserverField(
    modifier: Modifier = Modifier,
    value: String = "",
    onValueChange: (String) -> Unit = {},
    enabled: Boolean = true,
    state: HomeserverFieldState = HomeserverFieldState.Empty,
    error: String? = null,
) {
    TextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        enabled = enabled,
        singleLine = true,
        label = {
            Text(LocalContext.current.getString(R.string.homeserver_label))
        },
        placeholder = {
            Text(LocalContext.current.getString(R.string.homeserver_placeholder))
        },
        trailingIcon = {
            Icon(
                when(state) {
                    HomeserverFieldState.Empty -> Icons.Default.Build
                    HomeserverFieldState.Waiting -> Icons.Default.Create
                    HomeserverFieldState.Validating -> Icons.Default.Send
                    HomeserverFieldState.Error -> Icons.Default.Close
                    HomeserverFieldState.Done -> Icons.Default.Check
                },
                LocalContext.current.getString(R.string.homeserver_trailingicon_validating_description)
            )
        },
        supportingText = {
            Text(error ?: LocalContext.current.getString(R.string.homeserver_supporting))
        },
        isError = (error != null)
    )
}