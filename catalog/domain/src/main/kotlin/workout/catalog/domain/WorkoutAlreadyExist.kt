package workout.catalog.domain

interface WorkoutAlreadyExist {
    operator fun invoke(workoutText: List<Exercise>): Boolean
}
