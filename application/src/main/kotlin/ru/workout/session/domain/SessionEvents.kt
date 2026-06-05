package ru.workout.session.domain

import ru.workout.common.event.DomainEvent
import java.time.OffsetDateTime

sealed class SessionEvents(val sessionId: Int): DomainEvent {
    class SessionPreparedEvent(sessionId: Int): SessionEvents(sessionId) {
    }
    class InProgress(
        sessionId: Int,
        val startedAt: OffsetDateTime
    ): SessionEvents(sessionId) {
    }
}