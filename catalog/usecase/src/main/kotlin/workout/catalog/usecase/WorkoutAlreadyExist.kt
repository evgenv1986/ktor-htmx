package workout.catalog.usecase

interface WorkoutAlreadyExist {
    operator fun invoke(workoutText: String): Boolean
}
