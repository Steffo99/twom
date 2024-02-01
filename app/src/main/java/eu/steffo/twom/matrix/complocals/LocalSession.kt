package eu.steffo.twom.matrix.complocals

import androidx.compose.runtime.staticCompositionLocalOf
import org.matrix.android.sdk.api.session.Session

val LocalSession = staticCompositionLocalOf<Session?> { null }
