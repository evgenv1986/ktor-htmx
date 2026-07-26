package ru.workout.session.usecase.task

data class TaskSetView(
    val workoutId: Int,
    val setId: Int,
    val stepId: Int,
    val reps: Int,
    val exerciseName: String
) {
}