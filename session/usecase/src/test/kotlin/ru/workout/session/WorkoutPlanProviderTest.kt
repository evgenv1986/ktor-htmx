package ru.workout.session

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.string.shouldContain
import java.util.UUID

class WorkoutPlanProviderTest: StringSpec( {
    "mock workout plan provider should load workout plan by contract"{
        val mockProvider: WorkoutPlanProvider =
            MockWorkoutPlanProvider(WorkoutPlan(
                workoutPlanId: UUID = UUID.randomUUID(),
                listOf("Подтягивания", "Отжимания")
            ))
        val result = mockProvider.WorkoutPlanById(workoutPlanId = UUID.randomUUID())
        val plan = result.shouldBeRight()
        plan shouldContain "Подтягивания"
        plan shouldContain "Отжимания"
    }
})