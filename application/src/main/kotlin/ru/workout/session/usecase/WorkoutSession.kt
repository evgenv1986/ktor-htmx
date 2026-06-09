package ru.workout.session.usecase

import arrow.core.Either
import ru.workout.session.domain.WorkoutSession

fun interface SaveWorkoutSession{
    fun save(workout: WorkoutSession)
}
fun interface ExtractWorkoutSession{
    operator fun invoke(workoutPlanId: Int): Either<WorkoutSessionStoreError, WorkoutSession>
}

interface WorkoutSessionStoreError {

}
