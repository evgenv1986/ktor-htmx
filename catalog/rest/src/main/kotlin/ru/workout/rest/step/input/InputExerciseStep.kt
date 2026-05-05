package ru.workout.rest.step.input

import kotlinx.serialization.Serializable

@Serializable
data class InputExerciseStep(
    val exerciseName: String,
    val weight: Double,
    val reps: Int
)