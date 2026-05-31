package ru.workout.catalog.usecase

import ru.workout.catalog.domain.Workout
import ru.workout.catalog.usecase.SaveWorkout

open class MockSaveWorkout: SaveWorkout {
    var captured: Workout? = null
    override fun save(workout: Workout){
        captured = workout
    }
    fun captured(): Workout? {
        return captured
    }
}