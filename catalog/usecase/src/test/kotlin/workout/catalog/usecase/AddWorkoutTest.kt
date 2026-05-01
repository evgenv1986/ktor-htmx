package workout.catalog.usecase

import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.types.shouldBeInstanceOf
import workout.catalog.usecase.workout.Workout

class AddWorkoutTest: StringSpec( {
    "successfully added workout"{
        val idStore = MockIdStore()
        val saveWorkout = MockSaveWorkout()
        val alreadyExist = MockWorkoutAlreadyExist(result = false)
        val addWorkout = AddWorkoutUseCase(
            workoutAlreadyExist = alreadyExist,
            saveWorkout = saveWorkout,
            idStore = idStore
        )
        val workoutText = """
            1. Подтягивания с 10 кг на 1 повторов. 3 минуты работы. Максимальное число повторов в ТОТАЛ. 
            2. Отжимания на брусьях с 16 кг на 2 повторов. 3 минуты работы. Максимальное число повторов в ТОТАЛ.
        """.trimIndent()

        val workoutId: WorkoutId = addWorkout(workoutText).shouldBeRight()

        workoutId.shouldBeEqual(idStore.generate())
        val savedWorkout: Workout = saveWorkout.captured()!!
        savedWorkout.status shouldBe WorkoutStatus.ADDED
        val events: List<WorkoutEvent> = savedWorkout.popEvents()
        events.shouldHaveSize(1)
        events[0].shouldBeInstanceOf<WorkoutEvent.Added>()
    }
    "fail add empty workout"{
        val idStore = MockIdStore()
        val saveWorkout = MockSaveWorkout()
        val alreadyExist = MockWorkoutAlreadyExist(result = false)
        val addWorkout = AddWorkoutUseCase(
            workoutAlreadyExist = alreadyExist,
            saveWorkout = saveWorkout,
            idStore = idStore
        )
        val workoutText = ""
        val result = addWorkout(workoutText).shouldBeLeft()
        result.shouldBeInstanceOf<WorkoutUseCaseError.EmptyWorkoutUseCase>()
    }
    "can not add already existed workout"{
        val idStore = MockIdStore()
        val saveWorkout = MockSaveWorkout()
        val alreadyExist = MockWorkoutAlreadyExist(result = true)
        val addWorkout = AddWorkoutUseCase(
            workoutAlreadyExist = alreadyExist,
            saveWorkout = saveWorkout,
            idStore = idStore
        )
        val workoutText = """
            1. Подтягивания с 10 кг на 1 повторов. 3 минуты работы. Максимальное число повторов в ТОТАЛ. 
            2. Отжимания на брусьях с 16 кг на 2 повторов. 3 минуты работы. Максимальное число повторов в ТОТАЛ.
        """.trimIndent()

        val workoutId = addWorkout(workoutText).shouldBeLeft()
        workoutId.shouldBeInstanceOf<WorkoutUseCaseError.AlreadyExist>()
    }
})