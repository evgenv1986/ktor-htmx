package ru.workout.session.rest.html.workout

import kotlinx.serialization.Serializable

@Serializable
class RoundResponse(
    val roundId: String,
    val steps: List<StepResponse>) {
}