package ru.workout.catalog.usecase.workout

import workout.catalog.domain.workout.WorkoutAlreadyExist

open class MockWorkoutAlreadyExist(val result: Boolean): WorkoutAlreadyExist {
    private lateinit var captured: String

    override operator fun invoke(workoutText: String): Boolean{
        captured = workoutText
        return result
    }
}
