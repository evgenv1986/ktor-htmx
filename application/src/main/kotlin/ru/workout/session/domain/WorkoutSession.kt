package ru.workout.session.domain

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import ru.workout.common.event.DomainEvent
import java.time.OffsetDateTime

class WorkoutSession(
    val routine: SessionRoutine,
    val planId: Int,
    val exercises: List<String>,
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
        fun prepare(routine: SessionRoutine, sessionIdStore: SessionIdStore): WorkoutSession {
            return WorkoutSession(
                routine = routine,
                planId = routine.planId,
                exercises = routine.exercises,
                sessionId = sessionIdStore.nextId(),
                status = SessionStatus.PREPARED
            ).apply {
                addEvent(SessionEvents.SessionPreparedEvent(sessionId))
            }
        }

        fun from(plan: WorkoutPlan) {}
    }
    fun completeStep(stepId: String, actualReps: Int) {
        addEvent(
            StepEvents.StepCompletedEvent(
                actualReps,
                stepId,
                status = StepStatus.COMPLETED
            ))
    }
}
open class SessionStep {
    val status: StepStatus = TODO()
}
enum class StepStatus {
    COMPLETED
}
sealed class StepEvents(
): DomainEvent {
    abstract val stepId: String
    abstract val status: StepStatus
    data class StepCompletedEvent(
        val actualReps: Int,
        override val stepId: String,
        override val status: StepStatus
    ) : StepEvents() {

    }
}

interface WorkoutSessionError {
    object StatusNotPreparedError: WorkoutSessionError
}
