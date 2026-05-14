package ru.workout.catalog.usecase

import ru.workout.catalog.usecase.workout.WorkoutView
import workout.catalog.domain.TaskExercise
import workout.catalog.domain.WorkoutId
import workout.catalog.domain.WorkoutStatus
import workout.catalog.domain.tasks
import workout.catalog.domain.workoutId

fun workoutView(
    id: WorkoutId = workoutId(),
    tasks: List<TaskExercise> = tasks(),
    status: WorkoutStatus = WorkoutStatus.ADDED
): WorkoutView {
    return WorkoutView(
        id,
        tasks,
        status
    )
}