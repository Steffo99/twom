package eu.steffo.twom.room

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import eu.steffo.twom.matrix.TwoMMatrix
import org.matrix.android.sdk.api.session.Session

class RoomActivity : ComponentActivity() {
    companion object {
        const val ROOM_ID_EXTRA = "roomId"
    }

    private lateinit var session: Session

    private fun fetchLastSession() {
        Log.d("Main", "Fetching the last successfully authenticated session...")
        // FIXME: If this is null, it means that something launched this while no session was authenticated...
        session = TwoMMatrix.matrix.authenticationService().getLastAuthenticatedSession()!!
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
            RoomActivityScaffold(
                session = session,
                roomId = roomId!!,  // FIXME: Again, this should be set. Should.
                onBack = {
                    setResult(RESULT_CANCELED)
                    finish()
                },
            )
        }
    }
}