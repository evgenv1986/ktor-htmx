package workout.catalog.usecase

open class MockIdStore {
    fun generate(): WorkoutId {
        return WorkoutId(1)
    }

}
