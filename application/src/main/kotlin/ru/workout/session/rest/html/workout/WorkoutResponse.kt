package ru.workout.session.rest.html.workout

import kotlinx.serialization.Serializable
import ru.workout.session.rest.html.TaskResponse

@Serializable
class WorkoutResponse (
    val workoutId: String,
    val sets: List<SetResponse>
) {
}