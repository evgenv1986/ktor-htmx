package ru.workout.session.rest.html.workout

import kotlinx.serialization.Serializable

@Serializable
class SetResponse(
    val setId: String,
    val rounds: List<RoundResponse>
) {
}
@Serializable
class StepResponse(
    val stepId: String,
    val exerciseName: String,
    val reps: Int
) {
}
