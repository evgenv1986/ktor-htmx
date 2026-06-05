package ru.workout.session.domain

import ru.workout.common.event.DomainEvent
import java.util.UUID

sealed class SessionEvents: DomainEvent {
    class SessionCreatedEvent(val sessionId: Int): SessionEvents() {

    }
}