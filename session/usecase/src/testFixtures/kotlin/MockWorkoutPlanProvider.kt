import arrow.core.Either
import arrow.core.right
import io.kotest.matchers.shouldBe
import ru.workout.session.domain.WorkoutPlan
import ru.workout.session.usecase.usecase.WorkoutPlanProvider
import ru.workout.session.usecase.usecase.WorkoutProviderError

import java.util.UUID

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