package ru.workout.session.usecase

import ru.workout.catalog.rest.CompleteStepCommand

interface ICompleteStepUseCase {
    operator fun invoke(completeStepCommand: CompleteStepCommand)
}
