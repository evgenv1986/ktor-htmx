package ru.workout.training.usecase

import arrow.core.Either
import ru.workout.training.domain.WorkoutPlan
import java.util.UUID

interface WorkoutPlanProvider {
    fun workoutPlanById(workoutPlanId: UUID)
    : Either<WorkoutProviderError, WorkoutPlan>
}
sealed interface WorkoutProviderError {}
