package workout.catalog.usecase

import workout.catalog.usecase.workout.Workout
import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure

class AddWorkoutUseCase(
    val workoutAlreadyExist: MockWorkoutAlreadyExist,
    val saveWorkout: MockSaveWorkout,
    val idStore: MockIdStore
) {
    operator fun invoke(workoutText: String)
    :Either<WorkoutError, WorkoutId> = either {
        ensure (!workoutAlreadyExist(workoutText)){
            WorkoutError.AlreadyExist
        }
        val workout = Workout.add(idStore)
        saveWorkout.invoke(workout)
        workout.id
    }
}

interface WorkoutError {
    object AlreadyExist : WorkoutError
}
