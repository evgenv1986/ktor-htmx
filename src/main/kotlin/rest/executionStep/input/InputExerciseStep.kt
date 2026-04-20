package com.example.rest.executionStep.input

import kotlinx.serialization.Serializable

@Serializable
data class InputExerciseStep(
    val exerciseName: String,
    val weight: Double,
    val reps: Int
)