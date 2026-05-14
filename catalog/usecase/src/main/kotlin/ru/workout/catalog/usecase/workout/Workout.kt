package ru.workout.catalog.usecase.workout

import workout.catalog.domain.TaskExercise
import workout.catalog.domain.Workout
import workout.catalog.domain.WorkoutId
import workout.catalog.domain.WorkoutStatus

fun interface SaveWorkout{
    fun save(workout: Workout)
}

open class WorkoutView(
    val id: WorkoutId,
    val tasks: List<TaskExercise>,
    val status: WorkoutStatus
) {
}
