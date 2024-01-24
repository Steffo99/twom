package eu.steffo.twom.composables.fields

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.outlined.RemoveRedEye
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import eu.steffo.twom.R

@Composable
@Preview
fun PasswordField(
    modifier: Modifier = Modifier,
    value: String = "",
    onValueChange: (String) -> Unit = {},
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
) {
    var showPassword by rememberSaveable { mutableStateOf(false) }

    TextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        label = label,
        placeholder = placeholder,
        supportingText = supportingText,
        singleLine = true,

        visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),

        trailingIcon = {
            IconButton(
                onClick = {
                    showPassword = !showPassword
                }
            ) {
                Icon(
                    if(showPassword) {
                        Icons.Filled.RemoveRedEye
                    }
                    else {
                        Icons.Outlined.RemoveRedEye
                    },
                    if(showPassword) {
                        LocalContext.current.getString(R.string.password_hide)
                    }
                    else {
                        LocalContext.current.getString(R.string.password_show)
                    }
                )
            }
        }
    )
}