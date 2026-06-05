package ru.workout.session.domain

import ru.workout.common.event.DomainEvent
import java.util.UUID

class WorkoutSession(
    val routine: SessionRoutine,
    val sessionId: Int,
    val status: String,
): AggregateRoot() {
    companion object {
        fun prepare(routine: SessionRoutine, sessionId: Int): WorkoutSession {
            return WorkoutSession(
                routine = routine,
                sessionId = sessionId,
                status = "planned",
            ).apply {
                addEvent(SessionEvents.SessionCreatedEvent(sessionId))
            }
        }
    }
}