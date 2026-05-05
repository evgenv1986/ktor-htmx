package workout.catalog.domain.workout

interface WorkoutAlreadyExist {
    operator fun invoke(workoutText: String): Boolean
}
