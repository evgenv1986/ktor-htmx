package ru.workout.application.provider.workoutPlan

import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import ru.workout.application.training.domain.WorkoutPlan
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

/*
выполняемый подход задания-упражнения (The current approach of the task is exercises)
результирующий набор шаблонов повторений - repetitions template result set - resulting set of repetitions
active task -
    exercise: подтягивания
    totalReps: 35
    templateSetOfReps: [10, 15, 20, 23, 25, 27, 29, 30, 31, 32, 33, 34, 35]
    weight: 0
performed task -
    exercise: подтягивания

Set Of Reps for task (
    exercise: подтягивания
    total: 35
    ):{
        if
        return setOf<Reps>(10, 15, 20, 23, 25, 27, 29, 30, 31, 32, 33, 34, 35)
    }

 */