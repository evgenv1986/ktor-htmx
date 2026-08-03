package ru.workout.session.rest.html.workout

import kotlinx.serialization.Serializable

@Serializable
class StepCompletionResponse(
    val stepId: String,
    val repsResponse: RepsResponse
)