package eu.steffo.twom.ui.homeserver

import androidx.compose.material.icons.Icons
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


@Composable
@Preview
fun SelectHomeserverField(
    modifier: Modifier = Modifier,
    value: String = "",
    onValueChange: (String) -> Unit = {},
    enabled: Boolean = true,
    state: SelectHomeserverFieldState = SelectHomeserverFieldState.Empty,
) {
    TextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        enabled = enabled,
        singleLine = true,
        label = {
            Text(LocalContext.current.getString(R.string.selecthomeserver_input_label))
        },
        placeholder = {
            Text(LocalContext.current.getString(R.string.selecthomeserver_input_placeholder))
        },
        trailingIcon = {
            Icon(
                when(state) {
                    SelectHomeserverFieldState.Empty -> Icons.Default.Create
                    SelectHomeserverFieldState.Waiting -> Icons.Default.Create
                    SelectHomeserverFieldState.Validating -> Icons.Default.Send
                    SelectHomeserverFieldState.URLInvalid -> Icons.Default.Close
                    SelectHomeserverFieldState.FlowInvalid -> Icons.Default.Close
                    SelectHomeserverFieldState.Valid -> Icons.Default.Check
                },
                null
            )
        },
        supportingText = {
            Text(
                when(state) {
                    SelectHomeserverFieldState.Empty -> LocalContext.current.getString(R.string.selecthomeserver_input_supporting_empty)
                    SelectHomeserverFieldState.Waiting -> LocalContext.current.getString(R.string.selecthomeserver_input_supporting_waiting)
                    SelectHomeserverFieldState.Validating -> LocalContext.current.getString(R.string.selecthomeserver_input_supporting_validating)
                    SelectHomeserverFieldState.URLInvalid -> LocalContext.current.getString(R.string.selecthomeserver_input_supporting_urlinvalid)
                    SelectHomeserverFieldState.FlowInvalid -> LocalContext.current.getString(R.string.selecthomeserver_input_supporting_flowinvalid)
                    SelectHomeserverFieldState.Valid -> LocalContext.current.getString(R.string.selecthomeserver_input_supporting_valid)
                }
            )
        },
        isError = when(state) {
            SelectHomeserverFieldState.URLInvalid -> true
            SelectHomeserverFieldState.FlowInvalid -> true
            else -> false
        }
    )
}