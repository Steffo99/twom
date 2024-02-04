package eu.steffo.twom.viewroom.activities

import android.content.Context
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContract
import eu.steffo.twom.matrix.utils.TwoMMatrix
import eu.steffo.twom.viewroom.components.ViewRoomScaffold

class ViewRoomActivity : ComponentActivity() {
    companion object {
        const val ROOM_ID_EXTRA = "roomId"
    }

    class Contract : ActivityResultContract<String, Unit>() {
        override fun createIntent(context: Context, input: String): Intent {
            val intent = Intent(context, ViewRoomActivity::class.java)
            intent.putExtra(ROOM_ID_EXTRA, input)
            return intent
        }

        override fun parseResult(resultCode: Int, intent: Intent?) {}
    }

    override fun onStart() {
        super.onStart()

        val roomId = intent.getStringExtra(ROOM_ID_EXTRA)

        setContent {
            ViewRoomScaffold(
                session = TwoMMatrix.session!!,
                roomId = roomId!!,
            )
        }
    }
}