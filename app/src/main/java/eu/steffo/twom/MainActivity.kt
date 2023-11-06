package eu.steffo.twom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import eu.steffo.twom.ui.theme.TwoMTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TwoMTheme {
                // A surface container using the 'background' color from the theme
                ModalNavigationDrawer(
                    drawerContent = {
                        Drawer()
                    }
                ) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        Text(text = "Garasbici")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun Drawer() {
    ModalDrawerSheet {
        Text(text = "garasauto")
        Divider()
    }
}