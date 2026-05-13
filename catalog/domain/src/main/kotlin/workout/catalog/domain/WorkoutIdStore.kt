package workout.catalog.domain

interface WorkoutIdStore {
    fun generate(): WorkoutId
}