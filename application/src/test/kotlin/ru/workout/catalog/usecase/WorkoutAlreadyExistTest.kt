package ru.workout.catalog.usecase.ru.workout.catalog.usecase

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import ru.workout.catalog.domain.TaskExercise
import ru.workout.catalog.domain.Workout
import ru.workout.catalog.domain.WorkoutId
import ru.workout.catalog.domain.WorkoutStatus
import ru.workout.catalog.in_memoty_persistence.FindWorkoutByExercisesImp
import ru.workout.catalog.in_memoty_persistence.WorkoutAlreadyExitsInMemory
import java.util.UUID

class WorkoutAlreadyExistTest: StringSpec( {
    "should return true on workout already exist"{
        val exercise = listOf(TaskExercise("text"))
        val workoutId = WorkoutId(UUID.randomUUID())
        val workout = Workout(
            WorkoutStatus.DRAFT,
            workoutId,
            exercise
        )
        val data = mutableMapOf<WorkoutId, Workout>()
        data.put(workoutId, workout)
        val store = FindWorkoutByExercisesImp(data)
        val workoutExist = WorkoutAlreadyExitsInMemory(store)
        val result = workoutExist.invoke(exercise)
        result shouldBe true
    }
    "should return false on workout not exist"{
        val exercise = listOf(TaskExercise("text"))
        val workoutId = WorkoutId(UUID.randomUUID())
        val workout = Workout(
            WorkoutStatus.DRAFT,
            workoutId,
            exercise
        )
        val data = mutableMapOf<WorkoutId, Workout>()
        data.put(workoutId, workout)
        val store = FindWorkoutByExercisesImp(data)
        val workoutExist = WorkoutAlreadyExitsInMemory(store)
        val result = workoutExist.invoke(
            listOf(TaskExercise("some text"))
        )
        result shouldBe false
    }
})