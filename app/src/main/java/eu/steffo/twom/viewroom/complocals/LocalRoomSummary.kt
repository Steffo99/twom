package eu.steffo.twom.viewroom.complocals

import androidx.compose.runtime.staticCompositionLocalOf
import org.matrix.android.sdk.api.session.room.model.RoomSummary
import org.matrix.android.sdk.api.util.Optional

val LocalRoomSummary = staticCompositionLocalOf<Optional<RoomSummary>?> { null }
