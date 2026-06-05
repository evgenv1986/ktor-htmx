package ru.workout.session.domain

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import java.time.OffsetDateTime

class WorkoutSession(
    val routine: SessionRoutine,
    val sessionId: Int,
    var status: SessionStatus,
): AggregateRoot() {
    lateinit var startedAt: OffsetDateTime
    fun begin(startAt: OffsetDateTime
    ): Either<WorkoutSessionError, Unit> = either {
        ensure(status == SessionStatus.PREPARED){
            WorkoutSessionError.StatusNotPreparedError
        }
        startedAt = startAt
        status = SessionStatus.IN_PROGRESS
        addEvent(SessionEvents.InProgress(
                sessionId,
                startAt
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

interface WorkoutSessionError {
    object StatusNotPreparedError: WorkoutSessionError
}
