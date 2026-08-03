package ru.workout.session.rest.html.workout

import kotlinx.serialization.Serializable

@Serializable
class WorkoutResponse (
    val workoutId: String,
    val sets: List<SetResponse>,
    val completions: List<StepCompletionResponse>
) {
}