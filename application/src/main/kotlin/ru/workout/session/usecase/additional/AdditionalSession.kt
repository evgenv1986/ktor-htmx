package ru.workout.session.usecase.additional

import arrow.core.Either
import arrow.core.left
import arrow.core.raise.context.either
import arrow.core.right
import ru.workout.common.event.DomainEvent
import ru.workout.session.domain.AggregateRoot
import ru.workout.session.domain.SessionStatus
import ru.workout.session.domain.StepEvents
import ru.workout.session.domain.StepStatus

open class AdditionalSession(
    val tasks: List<AdditionalTask>,
    var status: SessionStatus
): AggregateRoot() {
    fun completeStep(
        step: AdditionalStep,
        task: AdditionalTask
    ): Either<AdditionalSessionStepCompletionError, Unit> = either {
//        val step = findStep(stepId).bind()
        task.completeStep(step)
            .mapLeft {
                when(it) {
                    is AdditionalTaskError.TaskNotInProgress ->
                        AdditionalSessionStepCompletionError.StepNotFound
                }
            }.bind()
            .apply{
                addEvent(StepEvents.StepCompletedEvent(
                    step.actualReps,
                    step.stepId,
                    StepStatus.COMPLETED
                ))
            }
        if (task.status() == AdditionalTaskStatus.COMPLETED){
            status = SessionStatus.COMPLETED
        }
    }
    fun findStep(stepId: String)
    :Either<AdditionalSessionStepCompletionError, AdditionalStep>{
        return tasks.flatMap { it.steps }
            .find { it.stepId == stepId }
            ?.right()
            ?: AdditionalSessionStepCompletionError.StepNotFound.left()
    }
    override fun popEvents(): List<DomainEvent> {
        val res = events
        events = ArrayList()
        events.clear()
        return res
    }

    fun status(): SessionStatus {
        return this.status
    }

}

sealed interface AdditionalSessionTaskError {
    object TaskNotInProgress: AdditionalSessionStepCompletionError
}

sealed interface AdditionalSessionStepCompletionError {
    object StepNotFound: AdditionalSessionStepCompletionError

}
