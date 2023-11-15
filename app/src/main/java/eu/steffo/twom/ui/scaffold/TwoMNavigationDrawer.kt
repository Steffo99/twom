package eu.steffo.twom.ui.scaffold

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun TwoMNavigationDrawer(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit = {},
) {
    ModalNavigationDrawer(
        drawerContent = {
            Drawer()
        }
    ) {
        Surface(
            modifier = modifier,
            color = MaterialTheme.colorScheme.background
        ) {
            content()
        }
    }
}

@Composable
fun Drawer() {
    ModalDrawerSheet {
        Text("garasauto")
    }
}
