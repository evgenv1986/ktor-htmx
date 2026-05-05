package ru.workout.rest.task

import kotlinx.serialization.Serializable

@Serializable
class TaskOfStep(
    val exerciseName: String,
    val weight: Double,
    val reps: Int
)