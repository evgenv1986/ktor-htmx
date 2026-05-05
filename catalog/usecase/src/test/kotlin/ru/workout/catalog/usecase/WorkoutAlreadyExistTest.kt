package ru.workout.catalog.usecase

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import ru.workout.catalog.usecase.workout.InMemoryWorkoutAlreadyExits
import ru.workout.catalog.usecase.workout.InMemoryWorkoutStore
import workout.catalog.domain.workout.Workout
import workout.catalog.domain.workout.WorkoutId
import workout.catalog.domain.workout.WorkoutStatus

class WorkoutAlreadyExistTest: StringSpec( {
    "should return true on workout already exist"{
        val workoutText = "text"
        val workoutId = WorkoutId(1)
        val workout = Workout(
            WorkoutStatus.DRAFT,
            workoutId,
            workoutText
        )
        val data = mutableMapOf<WorkoutId, Workout>()
        data.put(workoutId, workout)
        val store = InMemoryWorkoutStore(data)
        val workoutExist = InMemoryWorkoutAlreadyExits(store)
        val result = workoutExist.invoke(workoutText)
        result shouldBe true
    }
    "should return false on workout not exist"{
        val workoutText = "text"
        val workoutId = WorkoutId(1)
        val workout = Workout(
            WorkoutStatus.DRAFT,
            workoutId,
            workoutText
        )
        val data = mutableMapOf<WorkoutId, Workout>()
        data.put(workoutId, workout)
        val store = InMemoryWorkoutStore(data)
        val workoutExist = InMemoryWorkoutAlreadyExits(store)
        val result = workoutExist.invoke("some text")
        result shouldBe false
    }
})