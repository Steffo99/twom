package eu.steffo.twom.utils

import org.matrix.android.sdk.api.session.events.model.Event

data class RSVP(
    val event: Event?,
    val answer: RSVPAnswer,
    val comment: String,
)
