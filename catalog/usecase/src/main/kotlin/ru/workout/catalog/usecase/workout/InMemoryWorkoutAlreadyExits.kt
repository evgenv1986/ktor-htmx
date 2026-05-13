package ru.workout.catalog.usecase.workout

import workout.catalog.domain.TaskExercise
import workout.catalog.domain.WorkoutAlreadyExist

class InMemoryWorkoutAlreadyExits(val store: InMemoryWorkoutStore) : WorkoutAlreadyExist {
    override fun invoke(workoutText: List<TaskExercise>): Boolean {
        return store.invoke(workoutText).isRight()
    }
}