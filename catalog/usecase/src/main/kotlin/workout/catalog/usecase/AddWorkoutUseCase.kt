package workout.catalog.usecase

import workout.catalog.usecase.workout.Workout
import arrow.core.Either
import arrow.core.raise.either
import workout.catalog.usecase.workout.WorkoutError

class AddWorkoutUseCase(
    val workoutAlreadyExist: MockWorkoutAlreadyExist,
    val saveWorkout: MockSaveWorkout,
    val idStore: MockIdStore
) {
    operator fun invoke(workoutText: String)
    :Either<WorkoutUseCaseError, WorkoutId> = either {
//        ensure (!workoutAlreadyExist(workoutText)){
//            WorkoutError.AlreadyExist
//        }
        val workout = Workout.add(idStore, workoutAlreadyExist, workoutText)
            .mapLeft{ it.toUseCaseError() }
            .bind()
        saveWorkout.invoke(workout)
        workout.id
    }
}

sealed interface WorkoutUseCaseError {

}

fun WorkoutError.toUseCaseError() {
    TODO("Not yet implemented")
}