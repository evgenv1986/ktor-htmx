package workout.catalog.usecase

import workout.catalog.usecase.workout.Workout

open class MockSaveWorkout {
    var captured: Workout? = null
    operator fun invoke(workout: Workout){
        captured = workout
    }
    fun captured(): Workout? {
        return captured
    }
}
