package eu.steffo.twom.viewroom.effects

import androidx.compose.runtime.Composable
import eu.steffo.twom.matrix.complocals.LocalSession
import observePowerLevels
import kotlin.jvm.optionals.getOrNull

@Composable
fun canCurrentSessionInviteHere(): Boolean {
    val session = LocalSession.current ?: return false
    val powerLevelsRequest = observePowerLevels() ?: return false
    val powerLevels = powerLevelsRequest.getOrNull() ?: return false
    return powerLevels.second.isUserAbleToInvite(session.myUserId)
}