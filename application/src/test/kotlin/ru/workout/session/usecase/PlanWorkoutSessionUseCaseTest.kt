package ru.workout.session.usecase

import io.kotest.core.spec.style.StringSpec
import ru.workout.catalog.domain.workoutWithStatusAdd
import ru.workout.session.domain.WorkoutPlan
import java.util.UUID

class PlanWorkoutSessionUseCaseTest: StringSpec ({
    "session create from plan"{
        val plan = WorkoutPlan(
            catalogWorkoutId = UUID.randomUUID(),
            exercises = listOf("Приседания, Жим лёжа, Планка")
        )
    }

    "should prepare workout session by workout plan id - successfully"{
        val workout = workoutWithStatusAdd()
//        val workoutProvider
        val usecase = PrepareWorkoutUseCase()
        val planId = workout.id.value
        val prepareResult = usecase(planId)
        val workoutSession = prepareResult.shouldBeRight()
    }

})