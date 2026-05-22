package ru.workout.provider.workoutPlan

import arrow.core.Either
import ru.workout.training.domain.WorkoutPlan
import ru.workout.training.usecase.WorkoutPlanProvider
import ru.workout.training.usecase.WorkoutProviderError
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