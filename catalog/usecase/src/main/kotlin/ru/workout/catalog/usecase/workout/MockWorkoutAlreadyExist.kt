package ru.workout.catalog.usecase.workout

import workout.catalog.domain.workout.Exercise
import workout.catalog.domain.workout.WorkoutAlreadyExist

open class MockWorkoutAlreadyExist(val result: Boolean): WorkoutAlreadyExist {
    private lateinit var captured: List<Exercise>

    override operator fun invoke(exercises: List<Exercise>): Boolean{
        captured = exercises
        return result
    }
}
