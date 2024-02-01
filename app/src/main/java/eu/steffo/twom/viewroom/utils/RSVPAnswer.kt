package eu.steffo.twom.viewroom.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BuildCircle
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material.icons.outlined.HelpOutline
import androidx.compose.material.icons.outlined.HourglassEmpty
import androidx.compose.material.icons.outlined.MoreHoriz
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import eu.steffo.twom.R
import eu.steffo.twom.theme.utils.LaterColorRole
import eu.steffo.twom.theme.utils.MaybeColorRole
import eu.steffo.twom.theme.utils.NowayColorRole
import eu.steffo.twom.theme.utils.NullishColorRole
import eu.steffo.twom.theme.utils.StaticColorRole
import eu.steffo.twom.theme.utils.SureColorRole

enum class RSVPAnswer {
    // Will be there!
    SURE {
        override val value: String
            get() = "SURE"

        override val staticColorRole: StaticColorRole
            get() = SureColorRole

        override val icon: ImageVector
            get() = Icons.Outlined.CheckCircle

        @Composable
        override fun toLabel(): String =
            stringResource(R.string.room_rsvp_sure_label)

        @Composable
        override fun toResponse(): String =
            stringResource(R.string.room_rsvp_sure_response)

        @Composable
        override fun toCommentPlaceholder(): String =
            stringResource(R.string.room_rsvp_sure_placeholder)
    },

    // Will be there, but later!
    LATER {
        override val value: String
            get() = "LATER"

        override val staticColorRole: StaticColorRole
            get() = LaterColorRole

        override val icon: ImageVector
            get() = Icons.Outlined.Schedule

        @Composable
        override fun toLabel(): String =
            stringResource(R.string.room_rsvp_later_label)

        @Composable
        override fun toResponse(): String =
            stringResource(R.string.room_rsvp_later_response)

        @Composable
        override fun toCommentPlaceholder(): String =
            stringResource(R.string.room_rsvp_later_placeholder)
    },

    // Might be there...
    MAYBE {
        override val value: String
            get() = "MAYBE"

        override val staticColorRole: StaticColorRole
            get() = MaybeColorRole

        override val icon: ImageVector
            get() = Icons.Outlined.HelpOutline

        @Composable
        override fun toLabel(): String =
            stringResource(R.string.room_rsvp_maybe_label)

        @Composable
        override fun toResponse(): String =
            stringResource(R.string.room_rsvp_maybe_response)

        @Composable
        override fun toCommentPlaceholder(): String =
            stringResource(R.string.room_rsvp_maybe_placeholder)
    },

    // Won't be there.
    NOWAY {
        override val value: String
            get() = "NOWAY"

        override val staticColorRole: StaticColorRole
            get() = NowayColorRole

        override val icon: ImageVector
            get() = Icons.Outlined.Cancel

        @Composable
        override fun toLabel(): String =
            stringResource(R.string.room_rsvp_noway_label)

        @Composable
        override fun toResponse(): String =
            stringResource(R.string.room_rsvp_noway_response)

        @Composable
        override fun toCommentPlaceholder(): String =
            stringResource(R.string.room_rsvp_noway_placeholder)
    },

    // An option differing from the previous ones.
    UNKNOWN {
        override val value: String
            get() = ""

        override val staticColorRole: StaticColorRole
            get() = NullishColorRole

        override val icon: ImageVector
            get() = Icons.Outlined.BuildCircle

        @Composable
        override fun toLabel(): String? =
            null

        @Composable
        override fun toResponse(): String =
            stringResource(R.string.room_rsvp_unknown_response)

        @Composable
        override fun toCommentPlaceholder(): String =
            stringResource(R.string.room_rsvp_nullish_placeholder)
    },

    // The answer is still being loaded.
    LOADING {
        override val value: String
            get() = ""

        override val staticColorRole: StaticColorRole
            get() = NullishColorRole

        override val icon: ImageVector
            get() = Icons.Outlined.HourglassEmpty

        @Composable
        override fun toLabel(): String? = null

        @Composable
        override fun toResponse(): String =
            stringResource(R.string.loading)

        @Composable
        override fun toCommentPlaceholder(): String =
            stringResource(R.string.room_rsvp_nullish_placeholder)
    },

    // No answer has been provided yet.
    NONE {
        override val value: String
            get() = ""

        override val staticColorRole: StaticColorRole
            get() = NullishColorRole

        override val icon: ImageVector
            get() = Icons.Outlined.Circle

        @Composable
        override fun toLabel(): String? =
            null

        @Composable
        override fun toResponse(): String =
            stringResource(R.string.room_rsvp_none_response)

        @Composable
        override fun toCommentPlaceholder(): String =
            stringResource(R.string.room_rsvp_nullish_placeholder)
    },

    // Has been invited, but has not accepted yet.
    PENDING {
        override val value: String
            get() = ""

        override val staticColorRole: StaticColorRole
            get() = NullishColorRole

        override val icon: ImageVector
            get() = Icons.Outlined.MoreHoriz

        @Composable
        override fun toLabel(): String? =
            null

        @Composable
        override fun toResponse(): String =
            stringResource(R.string.room_rsvp_pending_response)

        @Composable
        override fun toCommentPlaceholder(): String =
            stringResource(R.string.room_rsvp_nullish_placeholder)
    };

    abstract val value: String
    abstract val staticColorRole: StaticColorRole
    abstract val icon: ImageVector

    @Composable
    abstract fun toLabel(): String?

    @Composable
    abstract fun toResponse(): String

    @Composable
    abstract fun toCommentPlaceholder(): String
}