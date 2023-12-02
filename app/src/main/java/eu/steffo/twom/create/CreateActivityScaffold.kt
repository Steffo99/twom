package eu.steffo.twom.create

import android.net.Uri
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import eu.steffo.twom.theme.TwoMTheme

@Composable
@Preview
fun CreateActivityScaffold(
    onClickBack: () -> Unit = {},
    onClickCreate: (name: String, description: String, avatarUri: Uri?) -> Unit = { _, _, _ -> },
) {
    TwoMTheme {
        Scaffold(
            topBar = {
                CreateActivityTopBar()
            },
            content = {
                CreateActivityContent(
                    modifier = Modifier.padding(it),
                    onClickCreate = onClickCreate,
                )
            }
        )
    }
}