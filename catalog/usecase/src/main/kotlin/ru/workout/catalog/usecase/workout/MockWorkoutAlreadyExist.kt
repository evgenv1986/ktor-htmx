package ru.workout.catalog.usecase.workout

import workout.catalog.domain.Exercise
import workout.catalog.domain.WorkoutAlreadyExist

open class MockWorkoutAlreadyExist(val result: Boolean): WorkoutAlreadyExist {
    private lateinit var captured: List<Exercise>

    override operator fun invoke(exercises: List<Exercise>): Boolean{
        captured = exercises
        return result
    }
}
