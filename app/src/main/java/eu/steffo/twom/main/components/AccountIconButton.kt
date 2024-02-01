package eu.steffo.twom.main.components

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.launch
import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import eu.steffo.twom.R
import eu.steffo.twom.avatar.components.AvatarUserId
import eu.steffo.twom.login.activities.LoginActivity
import eu.steffo.twom.matrix.complocals.LocalSession

@Composable
@Preview(showBackground = true)
fun AccountIconButton(
    modifier: Modifier = Modifier,
    processLogin: () -> Unit = {},
    processLogout: () -> Unit = {},
) {
    val session = LocalSession.current
    var expanded by remember { mutableStateOf(false) }

    val loginLauncher =
        rememberLauncherForActivityResult(LoginActivity.Contract()) {
            if (it != null) {
                processLogin()
            }
        }

    Box(modifier) {
        IconButton(
            onClick = { expanded = true },
        ) {
            if (session == null) {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = LocalContext.current.getString(R.string.main_account_label),
                )
            } else {
                AvatarUserId(
                    userId = session.myUserId,
                    contentDescription = LocalContext.current.getString(R.string.main_account_label),
                )
            }
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            if (session == null) {
                DropdownMenuItem(
                    text = {
                        Text(stringResource(R.string.main_account_login_text))
                    },
                    onClick = {
                        expanded = false
                        loginLauncher.launch()
                    }
                )
            } else {
                DropdownMenuItem(
                    text = {
                        Text(stringResource(R.string.main_account_logout_text))
                    },
                    onClick = {
                        expanded = false
                        processLogout()
                    }
                )
            }
        }
    }
}