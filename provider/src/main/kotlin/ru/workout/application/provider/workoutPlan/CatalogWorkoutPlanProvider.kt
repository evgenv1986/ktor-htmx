package ru.workout.application.provider.workoutPlan

import arrow.core.Either
import ru.workout.application.training.domain.WorkoutPlan
import ru.workout.application.training.usecase.WorkoutPlanProvider
import ru.workout.application.training.usecase.WorkoutProviderError
import java.util.UUID

class CatalogWorkoutPlanProvider()
    : WorkoutPlanProvider
{
    // пока оставим пустным
    override fun workoutPlanById(workoutPlanId: UUID)
    : Either<WorkoutProviderError, WorkoutPlan> {
        TODO("Not yet implemented")
    }
}