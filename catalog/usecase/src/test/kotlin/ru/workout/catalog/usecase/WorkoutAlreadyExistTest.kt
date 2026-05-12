package ru.workout.catalog.usecase

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import ru.workout.catalog.usecase.workout.InMemoryWorkoutAlreadyExits
import ru.workout.catalog.usecase.workout.InMemoryWorkoutStore
import workout.persistence.catalog.domain.workout.Exercise
import workout.persistence.catalog.domain.workout.Workout
import workout.persistence.catalog.domain.workout.WorkoutId
import workout.persistence.catalog.domain.workout.WorkoutStatus

class WorkoutAlreadyExistTest: StringSpec( {
    "should return true on workout already exist"{
        val exercise = listOf(Exercise("text"))
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
        val exercise = listOf(Exercise("text"))
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
            listOf(Exercise("some text"))
        )
        result shouldBe false
    }
})