package workout.catalog.usecase

open class MockWorkoutAlreadyExist(val result: Boolean) {
    private lateinit var captured: String

    operator fun invoke(workoutText: String): Boolean{
        captured = workoutText
        return result
    }
}
