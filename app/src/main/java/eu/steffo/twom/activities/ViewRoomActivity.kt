package eu.steffo.twom.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContract
import eu.steffo.twom.composables.viewroom.ViewRoomScaffold
import eu.steffo.twom.utils.TwoMGlobals
import org.matrix.android.sdk.api.session.Session

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

    private lateinit var session: Session

    private fun fetchLastSession() {
        Log.d("Main", "Fetching the last successfully authenticated session...")
        // FIXME: If this is null, it means that something launched this while no session was authenticated...
        session = TwoMGlobals.matrix.authenticationService().getLastAuthenticatedSession()!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // FIXME: Hopefully, if this succeeds, the session is also open... hopefully.
        fetchLastSession()
    }

    override fun onStart() {
        super.onStart()

        val roomId = intent.getStringExtra(ROOM_ID_EXTRA)

        setContent {
            ViewRoomScaffold(
                session = session,
                roomId = roomId!!,  // FIXME: Again, this should be set. Should.
            )
        }
    }
}