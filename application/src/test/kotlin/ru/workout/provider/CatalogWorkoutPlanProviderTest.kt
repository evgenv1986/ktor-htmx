package ru.workout.provider

import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import ru.workout.provider.workoutPlan.MockWorkoutPlanProvider
import ru.workout.session.domain.WorkoutPlan
import java.util.UUID

class CatalogWorkoutPlanProviderTest: StringSpec({
    "initialize training from template" {

    }
    "load workout from catalog"{
        var planId = UUID.randomUUID()
        var expectedExercises = listOf("Подтягивания", "Отжимания")
        var mockProvider = MockWorkoutPlanProvider(
            WorkoutPlan(planId, expectedExercises)
        )
        var result = mockProvider.workoutPlanById(planId)
        var plan: WorkoutPlan = result.shouldBeRight()
        plan.exercises shouldBe expectedExercises
        mockProvider.verifyInvoked(planId)


    }
})