package ru.workout.catalog.usecase.workout

import workout.catalog.domain.Exercise
import workout.catalog.domain.WorkoutAlreadyExist

class InMemoryWorkoutAlreadyExits(val store: InMemoryWorkoutStore) : WorkoutAlreadyExist {
    override fun invoke(workoutText: List<Exercise>): Boolean {
        return store.invoke(workoutText).isRight()
    }
}