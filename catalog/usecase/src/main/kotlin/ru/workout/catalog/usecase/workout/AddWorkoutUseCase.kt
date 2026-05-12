package ru.workout.catalog.usecase.workout

import arrow.core.Either
import arrow.core.raise.either
import workout.persistence.catalog.domain.workout.Exercise
import workout.persistence.catalog.domain.workout.Workout
import workout.persistence.catalog.domain.workout.WorkoutAlreadyExist
import workout.persistence.catalog.domain.workout.WorkoutError
import workout.persistence.catalog.domain.workout.WorkoutId

open class AddWorkoutUseCase(
    val workoutAlreadyExist: WorkoutAlreadyExist,
    val saveWorkout: SaveWorkout,
    val idStore: MockIdStore
) {
    operator fun invoke(exercises: List<Exercise>)
    :Either<WorkoutUseCaseError, WorkoutId> = either {
        val workout = Workout.add(
            idStore,
            workoutAlreadyExist,
            exercises
        )
            .mapLeft{ it.toUseCaseError() }
            .bind()
        saveWorkout.save(workout)
        workout.id
    }
}

sealed interface WorkoutUseCaseError {
    object AlreadyExist: WorkoutUseCaseError
    object EmptyWorkoutUseCase: WorkoutUseCaseError
}

fun WorkoutError.toUseCaseError() = when(this) {
    WorkoutError.AlreadyExist -> WorkoutUseCaseError.AlreadyExist
    WorkoutError.EmptyWorkout -> WorkoutUseCaseError.EmptyWorkoutUseCase
}