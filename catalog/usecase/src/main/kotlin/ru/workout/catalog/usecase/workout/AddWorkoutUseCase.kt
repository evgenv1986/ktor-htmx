package ru.workout.catalog.usecase.workout

import arrow.core.Either
import arrow.core.raise.either
import workout.catalog.domain.workout.Exercise
import workout.catalog.domain.workout.Workout
import workout.catalog.domain.workout.WorkoutAlreadyExist
import workout.catalog.domain.workout.WorkoutError
import workout.catalog.domain.workout.WorkoutId

open class AddWorkoutUseCase(
    val workoutAlreadyExist: WorkoutAlreadyExist,
    val saveWorkout: MockSaveWorkout,
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
        saveWorkout.invoke(workout)
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