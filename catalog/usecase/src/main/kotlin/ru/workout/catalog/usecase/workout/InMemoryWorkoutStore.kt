package ru.workout.catalog.usecase.workout

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensureNotNull
import workout.catalog.domain.TaskExercise
import workout.catalog.domain.Workout
import workout.catalog.domain.WorkoutId

interface RestoreWorkout {
    operator fun invoke(exercises: List<TaskExercise>): Either<WorkoutStoreError, Workout>
}
open class InMemoryWorkoutStore(
    val data: MutableMap<WorkoutId, Workout>
): RestoreWorkout {
    override fun invoke(exercises: List<TaskExercise>)
    : Either<WorkoutStoreError, Workout> = either {
        val inputExerciseNames = exercises.map { it.name() }
        val foundWorkout = data.values.find { workout ->
            workout.tasks.any { storedExercise ->
                storedExercise.name() in inputExerciseNames
            }
        }
        ensureNotNull(foundWorkout) {
            WorkoutStoreError.NotFound
        }
    }

}

sealed interface WorkoutStoreError {
    object NotFound : WorkoutStoreError
}
