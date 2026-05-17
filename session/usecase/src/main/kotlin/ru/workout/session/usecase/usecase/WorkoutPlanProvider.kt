package ru.workout.session.usecase.usecase

import arrow.core.Either
import ru.workout.session.domain.WorkoutPlan
import java.util.UUID

interface WorkoutPlanProvider {
    fun workoutPlanById(workoutPlanId: UUID): Either<WorkoutProviderError, WorkoutPlan>
    fun verifyInvoked(planId: UUID)
}

