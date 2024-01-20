package eu.steffo.twom.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.net.toFile
import androidx.lifecycle.lifecycleScope
import eu.steffo.twom.composables.main.MainScaffold
import eu.steffo.twom.utils.TwoMGlobals
import kotlinx.coroutines.launch
import org.matrix.android.sdk.api.session.Session
import org.matrix.android.sdk.api.session.room.model.create.CreateRoomParams
import org.matrix.android.sdk.api.session.room.model.create.CreateRoomPreset
import org.matrix.android.sdk.api.session.room.model.create.CreateRoomStateEvent


class MainActivity : ComponentActivity() {
    private var session: Session? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        TwoMGlobals.ensureMatrix(applicationContext)

        fetchLastSession()
        openSession()
        resetContent()
    }

    override fun onDestroy() {
        super.onDestroy()

        closeSession()
    }

    private fun fetchLastSession() {
        Log.d("Main", "Fetching the last successfully authenticated session...")
        session = TwoMGlobals.matrix.authenticationService().getLastAuthenticatedSession()
    }

    private fun openSession() {
        val currentSession = session
        Log.d("Main", "If possible, opening session: $currentSession")
        if (currentSession != null) {
            Log.d("Main", "Opening session: $currentSession")
            currentSession.open()
            currentSession.syncService().startSync(true)
            currentSession.addListener(OpenSessionListener(this::resetContent))

            Log.d(
                "Main",
                "Opened session, recomposing..."
            )
            resetContent()
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

    private fun resetContent() {
        Log.d("Main", "Recomposing...")
        setContent {
            // TODO: Check this with a clearer mind
            MainScaffold(
                session = session,
                processLogin = {
                    Log.d(
                        "Main",
                        "Login activity returned a successful result, trying to get session..."
                    )
                    fetchLastSession()
                    openSession()

                },
                processLogout = {
                    val signedOutSession = session!!

                    Log.d("Main", "Clicked logout, recomposing...")
                    session = null
                    resetContent()

                    Log.d("Main", "Done recomposing, now signing out...")
                    lifecycleScope.launch {
                        signedOutSession.signOutService().signOut(true)
                    }

                    Log.d("Main", "Done logging out!")
                },
                processCreate = {
                    lifecycleScope.launch {
                        val name = it.name
                        val description = it.description
                        val avatarUri = it.avatarUri

                        val currentSession = session

                        val createRoomParams = CreateRoomParams()

                        createRoomParams.name = name
                        createRoomParams.topic = description
                        createRoomParams.preset = CreateRoomPreset.PRESET_PRIVATE_CHAT
                        createRoomParams.roomType = TwoMGlobals.ROOM_TYPE
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
            )
        }
    }
}
