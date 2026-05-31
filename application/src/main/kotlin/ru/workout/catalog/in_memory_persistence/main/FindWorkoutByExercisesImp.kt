package ru.workout.catalog.in_memoty_persistence

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensureNotNull
import ru.workout.catalog.domain.TaskExercise
import ru.workout.catalog.domain.Workout
import ru.workout.catalog.domain.WorkoutId

interface FindWorkoutByExercises {
    operator fun invoke(exercises: List<TaskExercise>)
    : Either<WorkoutStoreError, Workout>
}
open class FindWorkoutByExercisesImp(
    val data: MutableMap<WorkoutId, Workout>
): FindWorkoutByExercises {
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
    data class ExtractById(val workoutPlanId: WorkoutId) : WorkoutStoreError
}
