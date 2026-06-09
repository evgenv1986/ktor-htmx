package ru.workout.provider.workoutPlan
import arrow.core.Either
import arrow.core.right
import io.kotest.matchers.shouldBe
import ru.workout.catalog.usecase.MockExtractWorkoutById
import ru.workout.session.domain.WorkoutPlan
import ru.workout.session.usecase.WorkoutPlanProvider
import ru.workout.session.usecase.WorkoutProviderError
import java.util.UUID

open class MockWorkoutPlanProvider(val workoutPlan: WorkoutPlan): WorkoutPlanProvider {
    private var planId: Int? = null

    override fun workoutPlanById(workoutPlanId: Int): Either<WorkoutProviderError, WorkoutPlan> {
        this.planId = workoutPlanId
        return workoutPlan.right()
    }

    fun verifyInvoked(planId: Int) {
        planId shouldBe this.planId
    }
}