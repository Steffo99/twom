package eu.steffo.twom.main

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
import eu.steffo.twom.matrix.LocalSession
import eu.steffo.twom.matrix.avatar.AvatarFromUserId

@Composable
@Preview(showBackground = true)
fun MainActivityAccountIconButton(
    modifier: Modifier = Modifier,
    onClickLogin: () -> Unit = {},
    onClickLogout: () -> Unit = {},
) {
    val session = LocalSession.current
    var expanded by remember { mutableStateOf(false) }

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
                AvatarFromUserId(
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
                        Text(stringResource(id = R.string.main_account_login_text))
                    },
                    onClick = {
                        expanded = false
                        onClickLogin()
                    }
                )
            } else {
                DropdownMenuItem(
                    text = {
                        Text(stringResource(id = R.string.main_account_logout_text))
                    },
                    onClick = {
                        expanded = false
                        onClickLogout()
                    }
                )
            }
        }
    }

}