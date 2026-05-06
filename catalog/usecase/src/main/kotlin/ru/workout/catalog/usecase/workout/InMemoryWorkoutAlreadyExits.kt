package ru.workout.catalog.usecase.workout

import workout.catalog.domain.workout.Exercise
import workout.catalog.domain.workout.WorkoutAlreadyExist

class InMemoryWorkoutAlreadyExits(val store: InMemoryWorkoutStore) : WorkoutAlreadyExist {
    override fun invoke(workoutText: List<Exercise>): Boolean {
        return store.invoke(workoutText).isRight()
    }
}