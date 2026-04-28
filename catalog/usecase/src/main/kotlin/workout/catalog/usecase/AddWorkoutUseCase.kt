package workout.catalog.usecase

import workout.catalog.usecase.workout.Workout

class AddWorkoutUseCase(
    val workoutAlreadyExist: MockWorkoutAlreadyExist,
    val saveWorkout: MockSaveWorkout
) {
    operator fun invoke(workout: String): Workout {
        return Workout()
    }
}