package eu.steffo.twom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import eu.steffo.twom.ui.fragment.HomeserverFragment
import eu.steffo.twom.ui.scaffold.TwoMMatrixProvider
import eu.steffo.twom.ui.theme.TwoMTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.hide()

        setContent {
            TwoMMatrixProvider {
                TwoMTheme {
                    HomeserverFragment()
                }
            }
        }
    }
}
