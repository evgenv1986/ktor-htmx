package workout.catalog.usecase

import workout.catalog.usecase.workout.Workout
import arrow.core.Either
import arrow.core.raise.either
import workout.catalog.usecase.workout.WorkoutError

open class AddWorkoutUseCase(
    val workoutAlreadyExist: WorkoutAlreadyExist,
    val saveWorkout: MockSaveWorkout,
    val idStore: MockIdStore
) {
    operator fun invoke(workoutText: String)
    :Either<WorkoutUseCaseError, WorkoutId> = either {
        val workout = Workout.add(idStore, workoutAlreadyExist, workoutText)
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