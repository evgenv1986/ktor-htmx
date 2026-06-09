package ru.workout.catalog.usecase

import arrow.core.Either
import ru.workout.session.domain.WorkoutSession
import ru.workout.session.usecase.ExtractWorkoutSession
import ru.workout.session.usecase.WorkoutSessionStoreError

open class MockExtractSessionStore(val session: WorkoutSession? = null): ExtractWorkoutSession {
    override fun invoke(workoutPlanId: Int): Either<WorkoutSessionStoreError, WorkoutSession> {
        TODO("Not yet implemented")
    }
}