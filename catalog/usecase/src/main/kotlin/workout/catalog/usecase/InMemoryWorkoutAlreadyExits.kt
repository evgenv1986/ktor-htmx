package workout.catalog.usecase

class InMemoryWorkoutAlreadyExits(val store: InMemoryWorkoutStore) : WorkoutAlreadyExist {
    override fun invoke(workoutText: String): Boolean {
        return store.invoke(workoutText).isRight()
    }
}