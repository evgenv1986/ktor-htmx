package ru.workout.session

import MockWorkoutPlanProvider
import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import java.util.UUID

class WorkoutPlanProviderTest: StringSpec( {
    "mock workout plan provider should load workout plan by contract"{
        val planId = UUID.randomUUID()
        val expectedExercises = listOf("Подтягивания", "Отжимания")
        val mockProvider: WorkoutPlanProvider =
            MockWorkoutPlanProvider(
                WorkoutPlan(planId, expectedExercises))
        val result = mockProvider.workoutPlanById(planId)
        val plan = result.shouldBeRight()
        plan.exercises shouldBe expectedExercises
        mockProvider.verifyInvoked(planId)
    }
//    "catalog workout plan provider should return workout plan"{
//        val planId = UUID.randomUUID()
//        val expectedExercises = listOf("Подтягивания", "Отжимания")
//        val workout = workoutWithStatusAdd()
//        val storage = LinkedHashMap<WorkoutId, Workout>()
//        storage[workout.id] = workout
//        val provider: WorkoutPlanProvider =
//            CatalogWorkoutPlanProvider(ExtractWorkoutStorage(data))
////        val result = mockProvider.workoutPlanById(planId)
////        val plan = result.shouldBeRight()
////        plan.exercises shouldBe expectedExercises
////        mockProvider.verifyInvoked(planId)
//    }
})

