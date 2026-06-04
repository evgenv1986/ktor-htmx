package ru.workout.session.usecase

import arrow.core.Either
import ru.workout.session.usecase.WorkoutPlan
import java.util.UUID

interface WorkoutPlanProvider {
    fun workoutPlanById(workoutPlanId: UUID)
    : Either<WorkoutProviderError, WorkoutPlan>
}
sealed interface WorkoutProviderError {
    object WorkoutNotFound: WorkoutProviderError
}
