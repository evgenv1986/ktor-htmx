package ru.workout.session.domain

import kotlinx.serialization.Serializable
import org.w3c.dom.Text
import ru.workout.catalog.domain.Workout

@Serializable
data class WorkoutPlan(
    val catalogWorkoutId: Int = 123,
    val exercises: List<String> = listOf<String>()
) {
    companion object {
        fun from(catalogWorkout: Workout): WorkoutPlan = WorkoutPlan(
            catalogWorkoutId = catalogWorkout.id.value,
            exercises = catalogWorkout.tasks.map { it.name }
        )
    }
}