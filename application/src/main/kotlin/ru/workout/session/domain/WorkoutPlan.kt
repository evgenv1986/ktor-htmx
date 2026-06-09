package ru.workout.session.domain

import ru.workout.catalog.domain.Workout

data class WorkoutPlan(
    val catalogWorkoutId: Int = 123,
    val exercises: List<String> = listOf<String>()) {
    companion object {
        fun from(catalogWorkout: Workout): WorkoutPlan = WorkoutPlan(
            catalogWorkoutId = catalogWorkout.id.value,
            exercises = catalogWorkout.tasks.map { it.name }
        )
    }
}