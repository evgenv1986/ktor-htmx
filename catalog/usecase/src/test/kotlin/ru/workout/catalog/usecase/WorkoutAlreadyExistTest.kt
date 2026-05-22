package ru.workout.catalog.usecase

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import ru.workout.catalog.usecase.workout.InMemoryWorkoutAlreadyExits
import ru.workout.catalog.usecase.workout.InMemoryWorkoutStore
import ru.workout.catalog.domain.TaskExercise
import ru.workout.catalog.domain.Workout
import ru.workout.catalog.domain.WorkoutId
import ru.workout.catalog.domain.WorkoutStatus

class WorkoutAlreadyExistTest: StringSpec( {
    "should return true on workout already exist"{
        val exercise = listOf(TaskExercise("text"))
        val workoutId = WorkoutId(1)
        val workout = Workout(
            WorkoutStatus.DRAFT,
            workoutId,
            exercise
        )
        val data = mutableMapOf<WorkoutId, Workout>()
        data.put(workoutId, workout)
        val store = InMemoryWorkoutStore(data)
        val workoutExist = InMemoryWorkoutAlreadyExits(store)
        val result = workoutExist.invoke(exercise)
        result shouldBe true
    }
    "should return false on workout not exist"{
        val exercise = listOf(TaskExercise("text"))
        val workoutId = WorkoutId(1)
        val workout = Workout(
            WorkoutStatus.DRAFT,
            workoutId,
            exercise
        )
        val data = mutableMapOf<WorkoutId, Workout>()
        data.put(workoutId, workout)
        val store = InMemoryWorkoutStore(data)
        val workoutExist = InMemoryWorkoutAlreadyExits(store)
        val result = workoutExist.invoke(
            listOf(TaskExercise("some text"))
        )
        result shouldBe false
    }
})