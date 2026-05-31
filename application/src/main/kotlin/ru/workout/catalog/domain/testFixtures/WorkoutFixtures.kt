package ru.workout.catalog.domain

import java.util.UUID

fun workoutWithStatusAdd(
    status: WorkoutStatus = WorkoutStatus.ADDED,
    id: WorkoutId = workoutId(),
    tasks: List<TaskExercise> = listOf(TaskExercise("pullUps"))
): Workout {
    val workout = Workout(
        status,
        id,
        tasks
    )
    workout.addEvent(WorkoutEvent.Added(workout.id))
    return workout
}
fun task(name: String = "pullups"): TaskExercise{
    return TaskExercise(name)
}
fun tasks(input: List<TaskExercise> = listOf(task())): List<TaskExercise>{
    return input
}
fun workoutId(): WorkoutId = WorkoutId(UUID.randomUUID())