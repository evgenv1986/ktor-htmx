package ru.workout.catalog.in_memory_persistence

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensureNotNull
import ru.workout.catalog.domain.Workout
import ru.workout.catalog.domain.WorkoutId
import ru.workout.catalog.in_memoty_persistence.WorkoutStoreError
import ru.workout.catalog.usecase.ExtractWorkout

open class ExtractWorkoutStorageById(
    val storage: LinkedHashMap<WorkoutId, Workout>
) : ExtractWorkout {
    override fun invoke(workoutId: WorkoutId)
    : Either<WorkoutStoreError, Workout> = either {
        val result = storage.values.find{ w ->
            w.id == workoutId }
        ensureNotNull(result){
            WorkoutStoreError.ExtractById(workoutId)
        }
    }
}