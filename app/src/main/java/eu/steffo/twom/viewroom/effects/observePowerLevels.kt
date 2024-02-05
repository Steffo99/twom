import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import eu.steffo.twom.viewroom.complocals.LocalRoom
import org.matrix.android.sdk.api.query.QueryStringValue
import org.matrix.android.sdk.api.session.events.model.toModel
import org.matrix.android.sdk.api.session.room.model.PowerLevelsContent
import org.matrix.android.sdk.api.session.room.powerlevels.PowerLevelsHelper
import java.util.Optional
import kotlin.jvm.optionals.getOrNull


private const val TAG = "observePowerLevels"


@Composable
fun observePowerLevels(): Optional<Pair<PowerLevelsContent, PowerLevelsHelper>>? {
    val roomRequest = LocalRoom.current

    if (roomRequest == null) {
        Log.v(TAG, "Requesting room information, not doing anything.")
        return null
    }

    val room = roomRequest.getOrNull()

    if (room == null) {
        Log.e(TAG, "Room was not found, not doing anything.")
        return null
    }

    val powerLevelsRequest by room.stateService().getStateEventLive(
        eventType = "m.room.power_levels",
        stateKey = QueryStringValue.IsEmpty,
    ).observeAsState()

    if (powerLevelsRequest == null) {
        Log.v(TAG, "Power level event is being requested.")
        return null
    }

    val powerLevels = powerLevelsRequest?.getOrNull()

    if (powerLevels == null) {
        Log.v(TAG, "No power level event has been found.")
        return Optional.empty()
    }

    val powerLevelsContent = powerLevels.content.toModel<PowerLevelsContent>()

    if (powerLevelsContent == null) {
        Log.e(TAG, "Could not deserialize power levels event.")
        return Optional.empty()
    }

    val powerLevelsHelper = PowerLevelsHelper(powerLevelsContent)

    return Optional.of(Pair(powerLevelsContent, powerLevelsHelper))
}