package ru.workout.session

import arrow.core.Either
import arrow.core.right
import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import java.util.UUID

data class WorkoutPlan(val workoutPlanId: UUID, val exercises: List<String>)
interface WorkoutPlanProvider {
    fun workoutPlanById(workoutPlanId: UUID): Either<WorkoutProviderError, WorkoutPlan>
    fun verifyInvoked(planId: UUID)
}

sealed interface WorkoutProviderError {
}

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
})

open class MockWorkoutPlanProvider(val workoutPlan: WorkoutPlan): WorkoutPlanProvider {
    private lateinit var planId: UUID

    override fun workoutPlanById(workoutPlanId: UUID): Either<WorkoutProviderError, WorkoutPlan> {
        this.planId = workoutPlanId
        return workoutPlan.right()
    }

    override fun verifyInvoked(planId: UUID) {
        planId shouldBe this.planId
    }
}

