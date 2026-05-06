package ru.workout.catalog.usecase.workout

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensureNotNull
import workout.catalog.domain.workout.Exercise
import workout.catalog.domain.workout.Workout
import workout.catalog.domain.workout.WorkoutId

interface RestoreWorkout {
    operator fun invoke(exercises: List<Exercise>): Either<WorkoutStoreError, Workout>
}
open class InMemoryWorkoutStore(
    val data: MutableMap<WorkoutId, Workout>
): RestoreWorkout {
    override fun invoke(exercises: List<Exercise>)
    : Either<WorkoutStoreError, Workout> = either {
        val inputExerciseNames = exercises.map { it.name() }
        val foundWorkout = data.values.find { workout ->
            workout.exercises.any { storedExercise ->
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
