package ru.workout.catalog.usecase.workout

import workout.catalog.domain.Workout

fun interface SaveWorkout{
    fun save(workout: Workout)
}

open class WorkoutView {

}
