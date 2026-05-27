package ru.workout.application.catalog.usecase

import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.types.shouldBeInstanceOf
//import ru.workout.catalog.usecase.workout.AddWorkoutUseCase
//import ru.workout.catalog.usecase.workout.MockIdStore
//import ru.workout.catalog.usecase.workout.MockWorkoutAlreadyExist
//import ru.workout.catalog.usecase.workout.WorkoutUseCaseError
//import ru.workout.common.event.DomainEvent
//import ru.workout.catalog.domain.TaskExercise
//import ru.workout.catalog.domain.Workout
//import ru.workout.catalog.domain.WorkoutEvent
//import ru.workout.catalog.domain.WorkoutId
//import ru.workout.catalog.domain.WorkoutStatus
//
//class AddWorkoutUseCaseTest: StringSpec( {
//    "successfully added workout"{
//        val idStore = MockIdStore()
//        val saveWorkout = MockSaveWorkout()
//        val alreadyExist = MockWorkoutAlreadyExist(result = false)
//        val usecase = AddWorkoutUseCase(
//            workoutAlreadyExist = alreadyExist,
//            saveWorkout = saveWorkout,
//            idStore = idStore
//        )
//        val workoutText = """
//            1. Подтягивания с 10 кг на 1 повторов. 3 минуты работы. Максимальное число повторов в ТОТАЛ. 
//            2. Отжимания на брусьях с 16 кг на 2 повторов. 3 минуты работы. Максимальное число повторов в ТОТАЛ.
//        """.trimIndent()
//        val exercices = listOf<TaskExercise>(
//            TaskExercise("1. Подтягивания с 10 кг на 1 повторов. 3 минуты работы. Максимальное число повторов в ТОТАЛ. "),
//            TaskExercise("2. Отжимания на брусьях с 16 кг на 2 повторов. 3 минуты работы. Максимальное число повторов в ТОТАЛ.")
//        )
//        val workoutId: WorkoutId = usecase(exercices).shouldBeRight()
//
//        workoutId.shouldBeEqual(idStore.generate())
//        val savedWorkout: Workout = saveWorkout.captured()!!
//        savedWorkout.status shouldBe WorkoutStatus.ADDED
//        val events: List<DomainEvent> = savedWorkout.popEvents()
//        events.shouldHaveSize(1)
//        events[0].shouldBeInstanceOf<WorkoutEvent.Added>()
//    }
//    "fail add empty workout"{
//        val idStore = MockIdStore()
//        val saveWorkout = MockSaveWorkout()
//        val alreadyExist = MockWorkoutAlreadyExist(result = false)
//        val usecase = AddWorkoutUseCase(
//            workoutAlreadyExist = alreadyExist,
//            saveWorkout = saveWorkout,
//            idStore = idStore
//        )
//        val workoutText = ""
//        val exercices = listOf<TaskExercise>()
//        val result = usecase(exercices).shouldBeLeft()
//        result.shouldBeInstanceOf<WorkoutUseCaseError.EmptyWorkoutUseCase>()
//    }
//    "can not add already existed workout"{
//        val idStore = MockIdStore()
//        val saveWorkout = MockSaveWorkout()
//        val alreadyExist = MockWorkoutAlreadyExist(result = true)
//        val usecase = AddWorkoutUseCase(
//            workoutAlreadyExist = alreadyExist,
//            saveWorkout = saveWorkout,
//            idStore = idStore
//        )
//        val workoutText = """
//            1. Подтягивания с 10 кг на 1 повторов. 3 минуты работы. Максимальное число повторов в ТОТАЛ. 
//            2. Отжимания на брусьях с 16 кг на 2 повторов. 3 минуты работы. Максимальное число повторов в ТОТАЛ.
//        """.trimIndent()
//
//        val exercices = listOf<TaskExercise>(
//            TaskExercise("1. Подтягивания с 10 кг на 1 повторов. 3 минуты работы. Максимальное число повторов в ТОТАЛ. "),
//            TaskExercise("2. Отжимания на брусьях с 16 кг на 2 повторов. 3 минуты работы. Максимальное число повторов в ТОТАЛ.")
//        )
//        val result = usecase(exercices).shouldBeLeft()
//
//        result.shouldBeInstanceOf<WorkoutUseCaseError.AlreadyExist>()
//    }
//})