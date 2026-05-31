package ru.workout.catalog.usecase

import arrow.core.Either
import ru.workout.catalog.domain.TaskExercise
import ru.workout.catalog.domain.Workout
import ru.workout.catalog.domain.WorkoutId
import ru.workout.catalog.domain.WorkoutStatus
import ru.workout.catalog.in_memoty_persistence.WorkoutStoreError
import ru.workout.session.domain.WorkoutPlan

fun interface SaveWorkout{
    fun save(workout: Workout)
}

open class WorkoutView(
    val id: WorkoutId,
    val tasks: List<TaskExercise>,
    val status: WorkoutStatus
) {
}

fun interface ExtractWorkout{
    operator fun invoke(workoutPlanId: WorkoutId): Either<WorkoutStoreError, Workout>
}