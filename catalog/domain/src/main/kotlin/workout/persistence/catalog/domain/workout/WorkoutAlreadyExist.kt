package workout.persistence.catalog.domain.workout

interface WorkoutAlreadyExist {
    operator fun invoke(workoutText: List<Exercise>): Boolean
}
