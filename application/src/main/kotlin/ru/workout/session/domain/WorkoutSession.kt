package ru.workout.session.domain

import java.time.OffsetDateTime

class WorkoutSession(
    val routine: SessionRoutine,
    val sessionId: Int,
    var status: SessionStatus,
): AggregateRoot() {
    lateinit var startedAt: OffsetDateTime
    fun begin(startedAt: OffsetDateTime) {
        this.startedAt = startedAt
        status = SessionStatus.IN_PROGRESS
        addEvent(SessionEvents.InProgress(
                sessionId,
                startedAt
        ))
    }

    companion object {
        fun prepare(routine: SessionRoutine, sessionId: Int): WorkoutSession {
            return WorkoutSession(
                routine = routine,
                sessionId = sessionId,
                status = SessionStatus.PREPARED
            ).apply {
                addEvent(SessionEvents.SessionPreparedEvent(sessionId))
            }
        }
    }
}