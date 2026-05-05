package ru.workout.catalog.usecase.workout

import workout.catalog.domain.workout.Workout

open class MockSaveWorkout {
    var captured: Workout? = null
    operator fun invoke(workout: Workout){
        captured = workout
    }
    fun captured(): Workout? {
        return captured
    }
}
