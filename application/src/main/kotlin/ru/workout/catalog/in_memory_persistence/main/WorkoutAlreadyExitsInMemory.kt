package ru.workout.catalog.in_memoty_persistence

import ru.workout.catalog.domain.TaskExercise
import ru.workout.catalog.domain.WorkoutAlreadyExist

class WorkoutAlreadyExitsInMemory(val store: FindWorkoutByExercisesImp) : WorkoutAlreadyExist {
    override fun invoke(workoutText: List<TaskExercise>): Boolean {
        return store.invoke(workoutText).isRight()
    }
}