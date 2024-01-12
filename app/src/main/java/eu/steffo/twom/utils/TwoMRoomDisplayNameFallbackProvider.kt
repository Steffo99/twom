package eu.steffo.twom.utils

import android.content.Context
import eu.steffo.twom.R
import org.matrix.android.sdk.api.provider.RoomDisplayNameFallbackProvider


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
