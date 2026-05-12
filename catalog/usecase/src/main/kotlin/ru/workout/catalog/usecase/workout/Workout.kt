package ru.workout.catalog.usecase.workout

import workout.persistence.catalog.domain.workout.Workout

fun interface SaveWorkout{
    fun save(workout: Workout)
}
