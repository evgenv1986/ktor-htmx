package ru.workout.catalog.usecase.workout

import workout.catalog.domain.TaskExercise
import workout.catalog.domain.WorkoutAlreadyExist

open class MockWorkoutAlreadyExist(val result: Boolean): WorkoutAlreadyExist {
    private lateinit var captured: List<TaskExercise>

    override operator fun invoke(exercises: List<TaskExercise>): Boolean{
        captured = exercises
        return result
    }
}
