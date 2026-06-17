package ru.workout.session.usecase.additional

import arrow.core.Either
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
    ): Either<AdditionalSessionStepCompletionError, Unit> {
        task.completeStep(step)

            .apply{
                addEvent(StepEvents.StepCompletedEvent(
                    step.actualTime,
                    step.stepId,
                    StepStatus.COMPLETED
                ))
            }
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
