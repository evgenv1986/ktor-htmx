//package ru.workout.application.provider.workoutPlan
//
//import arrow.core.Either
//import arrow.core.right
//import ru.workout.application.training.domain.WorkoutPlan
//import ru.workout.application.training.usecase.WorkoutPlanProvider
//import ru.workout.application.training.usecase.WorkoutProviderError
//import java.util.UUID
//
//open class MockWorkoutPlanProvider(val result: WorkoutPlan): WorkoutPlanProvider {
//    private var captured: UUID? = null
//
//    override fun workoutPlanById(workoutPlanId: UUID): Either<WorkoutProviderError, WorkoutPlan> {
//        this.captured = workoutPlanId
//        return result.right()
//    }
//
//    fun verifyInvoked(planId: UUID): Boolean {
//        return this.captured == planId
//    }
//
//}