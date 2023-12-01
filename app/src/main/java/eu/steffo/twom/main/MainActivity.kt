package eu.steffo.twom.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import eu.steffo.twom.create.CreateActivity
import eu.steffo.twom.login.LoginActivity
import eu.steffo.twom.matrix.TwoMMatrix
import eu.steffo.twom.room.RoomActivity
import kotlinx.coroutines.launch
import org.matrix.android.sdk.api.session.Session


class MainActivity : ComponentActivity() {
    private lateinit var loginLauncher: ActivityResultLauncher<Intent>
    private lateinit var roomLauncher: ActivityResultLauncher<Intent>
    private lateinit var createLauncher: ActivityResultLauncher<Intent>

    private var session: Session? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        TwoMMatrix.ensureMatrix(applicationContext)

        fetchLastSession()
        openSession()

        loginLauncher =
            registerForActivityResult(
                ActivityResultContracts.StartActivityForResult(),
                this::onLogin
            )

        roomLauncher =
            registerForActivityResult(
                ActivityResultContracts.StartActivityForResult(),
                this::onRoom
            )

        createLauncher =
            registerForActivityResult(
                ActivityResultContracts.StartActivityForResult(),
                this::onCreate
            )

        resetContent()
    }

    override fun onDestroy() {
        super.onDestroy()

        closeSession()
    }

    private fun fetchLastSession() {
        Log.d("Main", "Fetching the last successfully authenticated session...")
        session = TwoMMatrix.matrix.authenticationService().getLastAuthenticatedSession()
    }

    private fun openSession() {
        val currentSession = session
        Log.d("Main", "If possible, opening session: $currentSession")
        if (currentSession != null) {
            Log.d("Main", "Opening session: $currentSession")
            currentSession.open()
            currentSession.syncService().startSync(true)
        }
    }

    private fun closeSession() {
        val currentSession = session
        Log.d("Main", "If possible, closing session: $currentSession")
        if (currentSession != null) {
            Log.d("Main", "Closing session: $currentSession")
            currentSession.close()
        }
    }

    private fun onClickLogin() {
        Log.d("Main", "Clicked login, launching login activity...")
        val intent = Intent(applicationContext, LoginActivity::class.java)
        loginLauncher.launch(intent)
    }

    private fun onLogin(result: ActivityResult) {
        Log.d("Main", "Received result from login activity: $result")
        when (result.resultCode) {
            RESULT_OK -> {
                Log.d(
                    "Main",
                    "Login activity returned a successful result, trying to get session..."
                )
                fetchLastSession()
                session?.open()
                resetContent()
            }

            else -> {
                Log.d("Main", "Login activity was cancelled.")
            }
        }
    }

    private fun onClickLogout() {
        lifecycleScope.launch {
            Log.d("Main", "Clicked logout, signing out...")
            session!!.signOutService().signOut(true)
            session = null
            Log.d("Main", "Done logging out, recomposing...")
            resetContent()
        }
    }

    private fun onClickRoom(roomId: String) {
        Log.d("Main", "Clicked a room, launching room activity...")
        val intent = Intent(applicationContext, RoomActivity::class.java)
        intent.putExtra(RoomActivity.ROOM_ID_EXTRA, roomId)
        roomLauncher.launch(intent)
    }

    private fun onRoom(result: ActivityResult) {
        Log.d("Main", "Received result from room activity: $result")
    }

    private fun onClickCreate() {
        Log.d("Main", "Clicked the New button, launching create activity...")
        val intent = Intent(applicationContext, CreateActivity::class.java)
        createLauncher.launch(intent)
    }

    private fun onCreate(result: ActivityResult) {
        Log.d("Main", "Received result from create activity: $result")
    }

    private fun resetContent() {
        Log.d("Main", "Recomposing...")
        setContent {
            MatrixActivityScaffold(
                onClickLogin = this::onClickLogin,
                onClickLogout = this::onClickLogout,
                onClickRoom = this::onClickRoom,
                onClickCreate = this::onClickCreate,
                session = session,
            )
        }
    }
}
