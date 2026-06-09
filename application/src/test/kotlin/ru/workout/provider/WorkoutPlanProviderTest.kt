package ru.workout.session.usecase

import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import ru.workout.catalog.domain.Workout
import ru.workout.catalog.domain.WorkoutId
import ru.workout.catalog.domain.workoutWithStatusAdd
import ru.workout.catalog.in_memory_persistence.ExtractWorkoutStorageById
import ru.workout.provider.workoutPlan.CatalogWorkoutPlanProvider
import ru.workout.provider.workoutPlan.MockWorkoutPlanProvider
import ru.workout.session.domain.WorkoutPlan
import java.util.UUID

class WorkoutPlanProviderTest: StringSpec( {
    "mock workout plan provider should load workout plan by contract"{
        val planId = 123
        val expectedExercises = listOf("Подтягивания", "Отжимания")
        val mockProvider =
            MockWorkoutPlanProvider(
                WorkoutPlan(planId, expectedExercises)
            )
        val result = mockProvider.workoutPlanById(planId)
        val plan = result.shouldBeRight()
        plan.exercises shouldBe expectedExercises
        mockProvider.verifyInvoked(planId)
    }
    "catalog workout plan provider should return workout plan"{
        val planId = 123
        val expectedExercises = listOf("Подтягивания", "Отжимания")
        val workout = workoutWithStatusAdd()
        val storage = LinkedHashMap<WorkoutId, Workout>()
        storage[workout.id] = workout
        val provider = CatalogWorkoutPlanProvider(
                ExtractWorkoutStorageById(storage))
        val result = provider.workoutPlanById(planId)
        val plan = result.shouldBeRight()
        plan.exercises shouldBe expectedExercises

        val catalogWorkoutId = UUID.randomUUID()
//        CatalogWorkoutPlanProvider().prepare(catalogWorkoutId)
    }
})

