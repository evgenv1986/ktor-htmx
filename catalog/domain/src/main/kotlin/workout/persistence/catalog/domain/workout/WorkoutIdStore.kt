package workout.persistence.catalog.domain.workout

interface WorkoutIdStore {
    fun generate(): WorkoutId
}