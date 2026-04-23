package com.example.rest.executionStep.main.task

import kotlinx.serialization.Serializable

@Serializable
class TaskOfStep(
    val exerciseName: String,
    val weight: Double,
    val reps: Int
)