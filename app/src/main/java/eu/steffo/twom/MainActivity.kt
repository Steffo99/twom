package eu.steffo.twom

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import eu.steffo.twom.global.TwoMMatrix
import eu.steffo.twom.ui.theme.TwoMTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.hide()

        TwoMMatrix.initMatrix(applicationContext)
        TwoMMatrix.initSessionFromStorage()

        // Do this in a better way
        if(TwoMMatrix.session == null) {
            val homeserverIntent = Intent(applicationContext, HomeserverActivity::class.java)
            startActivity(homeserverIntent)
        }

        setContent {
            TwoMTheme {
                Text("Garasauto")
            }
        }
    }
}
