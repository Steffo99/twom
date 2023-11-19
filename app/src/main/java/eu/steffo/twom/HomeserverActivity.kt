package eu.steffo.twom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import eu.steffo.twom.ui.fragment.HomeserverFragment
import eu.steffo.twom.ui.theme.TwoMTheme


class HomeserverActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.hide()

        setContent {
            TwoMTheme {
                HomeserverFragment()
            }
        }
    }
}
