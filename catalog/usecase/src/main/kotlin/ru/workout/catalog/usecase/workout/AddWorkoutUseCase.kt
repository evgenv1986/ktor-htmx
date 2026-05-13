package ru.workout.catalog.usecase.workout

import arrow.core.Either
import arrow.core.raise.either
import workout.catalog.domain.TaskExercise
import workout.catalog.domain.Workout
import workout.catalog.domain.WorkoutAlreadyExist
import workout.catalog.domain.WorkoutError
import workout.catalog.domain.WorkoutId

open class AddWorkoutUseCase(
    val workoutAlreadyExist: WorkoutAlreadyExist,
    val saveWorkout: SaveWorkout,
    val idStore: MockIdStore
) {
    operator fun invoke(exercises: List<TaskExercise>)
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