package ru.workout.catalog.usecase

import ru.workout.catalog.usecase.workout.SaveWorkout
import workout.persistence.catalog.domain.workout.Workout

open class MockSaveWorkout: SaveWorkout {
    var captured: Workout? = null
    override fun save(workout: Workout){
        captured = workout
    }
    fun captured(): Workout? {
        return captured
    }
}