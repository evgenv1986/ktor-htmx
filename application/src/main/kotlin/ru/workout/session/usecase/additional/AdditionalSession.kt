package ru.workout.session.usecase.additional

import arrow.core.Either
import arrow.core.raise.context.bind
import arrow.core.raise.context.either
import ru.workout.common.event.DomainEvent
import ru.workout.session.domain.AggregateRoot
import ru.workout.session.domain.SessionError
import ru.workout.session.domain.SessionStatus
import ru.workout.session.domain.StepEvents
import ru.workout.session.domain.StepStatus

open class AdditionalSession(tasks: Any, status: SessionStatus
): AggregateRoot() {
    fun completeStep(
        step: AdditionalStep,
        task: AdditionalTask
    ): Either<AdditionalSessionStepCompletionError, Unit> = either {
        val result = task.completeStep(step)
            .mapLeft {
                when(it) {
                    is AdditionalTaskError.TaskNotInProgress ->
                        AdditionalSessionStepCompletionError.TaskNotInProgress
                }
            }.bind()
            .apply{
                addEvent(StepEvents.StepCompletedEvent(
                    step.actualTime,
                    step.stepId,
                    StepStatus.COMPLETED
                ))
            }

//        val result = task.completeStep(step).fold(
//            ifLeft = {error ->
//                when(error){
//                    is AdditionalTaskError.TaskNotInProgress -> AdditionalSessionStepCompletionError.TaskNotInProgress
//                }
//            },
//            ifRight = {}
//        )

    }

    override fun popEvents(): List<DomainEvent> {
        val res = events
        events = ArrayList()
        events.clear()
        return res
    }

}

sealed interface AdditionalSessionStepCompletionError {
    object TaskNotInProgress: AdditionalSessionStepCompletionError
}
