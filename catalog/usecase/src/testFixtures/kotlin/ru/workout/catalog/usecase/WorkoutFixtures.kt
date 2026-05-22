package ru.workout.catalog.usecase

import ru.workout.catalog.usecase.workout.WorkoutView
import ru.workout.catalog.domain.TaskExercise
import ru.workout.catalog.domain.WorkoutId
import ru.workout.catalog.domain.WorkoutStatus
import ru.workout.catalog.domain.tasks
import ru.workout.catalog.domain.workoutId

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