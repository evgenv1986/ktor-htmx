package ru.workout.session.domain

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import arrow.core.raise.ensureNotNull
import ru.workout.common.event.DomainEvent
import java.time.OffsetDateTime

class WorkoutSession(
//    val routine: SessionRoutine = SessionRoutine(planId = 0, exercises = listOf("empty")),
//    val planId: Int = 0,
//    val exercises: List<String> = listOf("empty"),
//    val sessionId: Int = 0,
    val stepsTemplate: MutableMap<String, SessionStep>? = mutableMapOf<String, SessionStep>(),
    var status: SessionStatus?

): AggregateRoot() {
    lateinit var startedAt: OffsetDateTime
//    lateinit var status: SessionStatus
    val stepsActual: MutableMap<String, SessionStep> = mutableMapOf<String, SessionStep>()

//    fun begin(startAt: OffsetDateTime
//    ): Either<WorkoutSessionError, Unit> = either {
//        ensure(status == SessionStatus.PREPARED){
//            WorkoutSessionError.StatusNotPreparedError
//        }
//        startedAt = startAt
//        status = SessionStatus.IN_PROGRESS
//        addEvent(SessionEvents.InProgress(
//                sessionId,
//                startAt
//        ))
//    }
    companion object {
//    fun create(stepTemplate: Map<String, Int>, status: SessionStatus): WorkoutSession {
//        return WorkoutSession()
//    }
//        fun prepare(routine: SessionRoutine, sessionIdStore: SessionIdStore): WorkoutSession {
//            return WorkoutSession(
//                routine = routine,
//                planId = routine.planId,
//                exercises = routine.exercises,
//                sessionId = sessionIdStore.nextId(),
//                status = SessionStatus.PREPARED
//            ).apply {
//                addEvent(SessionEvents.SessionPreparedEvent(sessionId))
//            }
//        }

//        fun from(plan: WorkoutPlan) {}
    }
    fun completeStep(stepId: String, actualReps: Int
    ): Either<SessionError, Unit> = either {
        ensure(status == SessionStatus.IN_PROGRESS){
            SessionError.StateIsNotInProgress
        }

        val stepTemplate = stepsTemplate?.get(stepId)
        ensureNotNull(stepTemplate){
            SessionError.StepNotFound
        }

        stepsActual.put(stepId,
            SessionStep(stepId, actualReps, StepStatus.COMPLETED))

        stepTemplate.status = StepStatus.COMPLETED

        // refresh step status
//        if (stepTemplateReps == actualReps){
//            stepsTemplate[stepId].status = StepStatus.COMPLETEDFull
//        }
//        if (stepTemplateReps > actualReps){
//            stepsTemplate[stepId].status = StepStatus.COMPLETEDNotFull
//        }
        addEvent(
            StepEvents.StepCompletedEvent(
                actualReps,
                stepId,
                status = StepStatus.COMPLETED
            ))
    }

    fun stepTemplatesCount(): Int? {
        return stepsTemplate?.count()
    }

    fun stepStatus(stepId: String): StepStatus {
        val step = stepsTemplate?.get(stepId)
        if (step != null){
            return step.status
        } else throw IllegalArgumentException("step not found")
    }
}

interface SessionError {
    object StateIsNotInProgress : SessionError
    object StepNotFound : SessionError
}

open class SessionStep(
    val stepId: String,
    val actualReps: Int,
    var status: StepStatus
) {

}
enum class StepStatus {
    COMPLETED,
    PLANNED
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
