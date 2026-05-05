package ru.workout.catalog.usecase.workout

import workout.catalog.domain.workout.WorkoutAlreadyExist

class InMemoryWorkoutAlreadyExits(val store: InMemoryWorkoutStore) : WorkoutAlreadyExist {
    override fun invoke(workoutText: String): Boolean {
        return store.invoke(workoutText).isRight()
    }
}