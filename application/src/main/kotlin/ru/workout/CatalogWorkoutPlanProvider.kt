package ru.workout

import arrow.core.Either
import ru.workout.training.usecase.WorkoutPlanProvider
import java.util.UUID

class CatalogWorkoutPlanProvider()
    : WorkoutPlanProvider
{
    // пока оставим пустным
//    override fun workoutPlanById(workoutPlanId: UUID): Either<WorkoutProviderError, WorkoutPlan> {
//        TODO("Not yet implemented")
//    }
}