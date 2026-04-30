package workout.catalog.usecase

open class MockWorkoutAlreadyExist(val result: Boolean): WorkoutAlreadyExist {
    private lateinit var captured: String

    override operator fun invoke(workoutText: String): Boolean{
        captured = workoutText
        return result
    }
}
