package eu.steffo.twom.ui.scaffold

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.platform.LocalContext
import eu.steffo.twom.R
import org.matrix.android.sdk.api.Matrix
import org.matrix.android.sdk.api.MatrixConfiguration
import org.matrix.android.sdk.api.provider.RoomDisplayNameFallbackProvider
import org.matrix.android.sdk.api.session.Session

val LocalMatrix = compositionLocalOf<Matrix?> { null }
val LocalSession = compositionLocalOf<Session?> { null }

@Composable
fun TwoMMatrixProvider(
    content: @Composable () -> Unit = {},
) {
    val matrix = Matrix(
        context = LocalContext.current,
        matrixConfiguration = MatrixConfiguration(
            applicationFlavor = "TwoM",
            roomDisplayNameFallbackProvider = TwoMRoomDisplayNameFallbackProvider(LocalContext.current)
        )
    )

    // TODO: The session should be opened somewhere, I think.
    val session = matrix.authenticationService().getLastAuthenticatedSession()

    CompositionLocalProvider(
        LocalMatrix provides matrix,
        LocalSession provides session,
    ) {
        content()
    }
}


class TwoMRoomDisplayNameFallbackProvider(
    private val context: Context
) : RoomDisplayNameFallbackProvider {

    override fun getNameFor1member(name: String): String {
        return context.getString(R.string.room_name_fallback_members_1).format(name)
    }

    override fun getNameFor2members(name1: String, name2: String): String {
        return context.getString(R.string.room_name_fallback_members_2).format(name1, name2)
    }

    override fun getNameFor3members(name1: String, name2: String, name3: String): String {
        return context.getString(R.string.room_name_fallback_members_3).format(name1, name2, name3)
    }

    override fun getNameFor4members(
        name1: String,
        name2: String,
        name3: String,
        name4: String
    ): String {
        return context.getString(R.string.room_name_fallback_members_4).format(name1, name2, name3, name4)
    }

    override fun getNameFor4membersAndMore(
        name1: String,
        name2: String,
        name3: String,
        remainingCount: Int
    ): String {
        return context.getString(R.string.room_name_fallback_members_more).format(name1, name2, name3, remainingCount)
    }

    override fun getNameForEmptyRoom(isDirect: Boolean, leftMemberNames: List<String>): String {
        return context.getString(R.string.room_name_fallback_members_0)
    }

    override fun getNameForRoomInvite(): String {
        return context.getString(R.string.room_name_fallback_invite)
    }
}