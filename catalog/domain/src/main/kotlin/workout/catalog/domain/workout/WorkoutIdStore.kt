package workout.catalog.domain.workout

interface WorkoutIdStore {
    fun generate(): WorkoutId
}