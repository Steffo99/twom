package eu.steffo.twom.create

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class CreateActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CreateActivityScaffold(
                onClickBack = {
                    setResult(RESULT_CANCELED)
                    finish()
                },
                onClickCreate = {
                    setResult(RESULT_OK)
                    finish()
                },
            )
        }
    }
}