package ru.workout.session.rest.task

import kotlinx.serialization.Serializable

@Serializable
data class TaskResponse(
    val workoutId: Int,
    val setId: Int,
    val stepId: Int,
    val reps: Int,
    val exerciseName: String
) {}