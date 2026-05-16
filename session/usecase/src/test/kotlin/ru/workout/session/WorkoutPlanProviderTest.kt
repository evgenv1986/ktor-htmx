package ru.workout.session

import io.kotest.core.spec.style.StringSpec
import java.util.UUID

class WorkoutPlanProviderTest: StringSpec( {
    "mock workout plan provider should load workout plan by contract"{
        val mockProvider: WorkoutPlanProvider =
            MockWorkoutPlanProvider(WorkoutPlan(
                workoutId: UUID = UUID.randomUUID(),
                listOf("Подтягивания", "Отжимания")
            ))
    }
})