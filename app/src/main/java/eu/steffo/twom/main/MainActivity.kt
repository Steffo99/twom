package eu.steffo.twom.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toFile
import androidx.lifecycle.lifecycleScope
import eu.steffo.twom.create.CreateActivity
import eu.steffo.twom.login.LoginActivity
import eu.steffo.twom.matrix.TwoMMatrix
import eu.steffo.twom.room.RoomActivity
import kotlinx.coroutines.launch
import org.matrix.android.sdk.api.session.Session
import org.matrix.android.sdk.api.session.room.model.create.CreateRoomParams
import org.matrix.android.sdk.api.session.room.model.create.CreateRoomPreset
import org.matrix.android.sdk.api.session.room.model.create.CreateRoomStateEvent


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
            currentSession.addListener(OpenSessionListener(this::resetContent))
        }
    }

    private class OpenSessionListener(private val resetContent: () -> Unit) : Session.Listener {
        override fun onSessionStarted(session: Session) {
            resetContent()
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
                openSession()
            }
            else -> {
                Log.d("Main", "Login activity was cancelled.")
            }
        }
    }

    private fun onClickLogout() {
        lifecycleScope.launch {
            val signedOutSession = session!!

            Log.d("Main", "Clicked logout, recomposing...")
            session = null
            resetContent()

            Log.d("Main", "Done recomposing, now signing out...")
            signedOutSession.signOutService().signOut(true)

            Log.d("Main", "Done logging out!")
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
        if (result.resultCode == RESULT_OK) {
            val intent: Intent = result.data!!
            val name = intent.getStringExtra(CreateActivity.NAME_EXTRA)
            val description = intent.getStringExtra(CreateActivity.DESCRIPTION_EXTRA)
            @Suppress("DEPRECATION") val avatarUri =
                intent.getParcelableExtra<Uri>(CreateActivity.AVATAR_EXTRA)

            if (name == null) {
                Log.w("Main", "Result from create activity did not have `name` extra set")
                return
            }
            if (description == null) {
                Log.w("Main", "Result from create activity did not have `description` extra set")
                return
            }

            lifecycleScope.launch {
                val currentSession = session

                val createRoomParams = CreateRoomParams()

                createRoomParams.name = name
                createRoomParams.topic = description
                createRoomParams.preset = CreateRoomPreset.PRESET_PRIVATE_CHAT
                createRoomParams.roomType = TwoMMatrix.ROOM_TYPE
                createRoomParams.initialStates = mutableListOf(
                    CreateRoomStateEvent(
                        type = "m.room.power_levels",
                        content = mapOf(
                            // Users start with a power level of 0
                            "users_default" to 0,
                            // Allow only the party creator to send arbitrary events
                            "events_default" to 100,
                            // Allow only the party creator to send arbitrary states
                            "state_default" to 100,

                            // Allow only party officers to send invites
                            "invite" to 50,
                            // Allow only party officers to kick invitees
                            "kick" to 50,
                            // Allow only party officers to ban invitees
                            "ban" to 50,
                            // Allow only party officers to redact other people's events
                            "redact" to 50,

                            "notifications" to mapOf(
                                // Allow only party officers to ping the room
                                "room" to 50,
                            ),

                            "events" to mapOf(
                                // Allow party officers to rename the room
                                "m.room.name" to 50,
                                // Allow party officers to change the room avatar
                                "m.room.avatar" to 50,
                                // Allow party officers to change the room topic
                                "m.room.topic" to 50,
                                // Allow everyone to redact their own states
                                "m.room.redaction" to 0,
                                // Allow everyone to set RSVPs
                                // FIXME: Do we really want everyone to set RSVPs? Maybe we should use m.room.member instead?
                                "eu.steffo.twom.rsvp" to 0,
                            ),

                            "users" to mapOf(
                                // Give ourselves admin permissions
                                session!!.myUserId to 100,
                            )
                        )
                    )
                )

                when (avatarUri?.toFile()?.isFile) {
                    false -> {
                        Log.e(
                            "Main",
                            "Avatar has been deleted from cache before room could possibly be created, ignoring..."
                        )
                    }

                    true -> {
                        Log.d(
                            "Main",
                            "Avatar seems to exist at: $avatarUri"
                        )
                        createRoomParams.avatarUri = avatarUri
                    }

                    null -> {
                        Log.d(
                            "Main",
                            "Avatar was not set, ignoring..."
                        )
                    }
                }

                Log.d(
                    "Main",
                    "Creating room '$name' with description '$description' and avatar '$avatarUri'..."
                )
                val roomId = currentSession!!.roomService().createRoom(createRoomParams)

                Log.d(
                    "Main",
                    "Created  room '$name' with description '$description' and avatar '$avatarUri': $roomId"
                )
            }
        }
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
