package ru.workout.session.usecase

import ru.workout.catalog.domain.Workout
import java.util.UUID

data class WorkoutPlan(
    val catalogWorkoutId: UUID = UUID.randomUUID(),
    val exercises: List<String> = listOf<String>()) {
    companion object {
        fun from(catalogWorkout: Workout): WorkoutPlan = WorkoutPlan(
            catalogWorkoutId = catalogWorkout.id.value,
            exercises = catalogWorkout.tasks.map { it.name }
        )
    }
}