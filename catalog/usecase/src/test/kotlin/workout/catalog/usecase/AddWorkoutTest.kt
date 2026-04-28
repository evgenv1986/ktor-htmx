package workout.catalog.usecase

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import workout.catalog.usecase.workout.Workout
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.types.shouldBeInstanceOf

class AddWorkoutTest: StringSpec( {
    "successfully added workout"{
        val addWorkout = AddWorkoutUseCase(
            workoutAlreadyExist = MockWorkoutAlreadyExist(result = false),
            saveWorkout = MockSaveWorkout()
        )
        val workoutText = """
            1. Подтягивания с 10 кг на 1 повторов. 3 минуты работы. Максимальное число повторов в ТОТАЛ. 
            2. Отжимания на брусьях с 16 кг на 2 повторов. 3 минуты работы. Максимальное число повторов в ТОТАЛ.
        """.trimIndent()
        val workout: Workout = addWorkout(workoutText)
        workout.status shouldBe WorkoutStatus.ADDED
        val events: List<String> = workout.popEvents()
        events.shouldHaveSize(1)
        val event = events[0].shouldBeInstanceOf<WorkoutEvents.Added>()
//        event.retailCustomerId shouldBe retailCustomer.id
    }

})