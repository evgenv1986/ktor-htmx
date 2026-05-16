package ru.workout.session

import arrow.core.Either
import java.util.UUID

interface WorkoutPlanProvider {
    fun workoutPlanById(workoutPlanId: UUID): Either<WorkoutProviderError, WorkoutPlan>
    fun verifyInvoked(planId: UUID)
}

