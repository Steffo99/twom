package eu.steffo.twom.room

import androidx.compose.runtime.staticCompositionLocalOf
import org.matrix.android.sdk.api.session.room.Room
import java.util.Optional

val LocalRoom = staticCompositionLocalOf<Optional<Room>?> { null }
