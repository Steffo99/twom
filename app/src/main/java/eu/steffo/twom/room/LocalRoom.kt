package eu.steffo.twom.room

import androidx.compose.runtime.staticCompositionLocalOf
import org.matrix.android.sdk.api.session.room.model.RoomSummary

val LocalRoom = staticCompositionLocalOf<RoomSummary?> { null }
