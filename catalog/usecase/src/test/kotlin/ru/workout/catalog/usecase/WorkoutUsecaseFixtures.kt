package ru.workout.catalog.usecase

import ru.workout.catalog.usecase.workout.AddWorkoutUseCase
import ru.workout.catalog.usecase.workout.MockIdStore
import ru.workout.catalog.usecase.workout.MockSaveWorkout
import ru.workout.catalog.usecase.workout.MockWorkoutAlreadyExist

fun addWorkoutUsecase(): AddWorkoutUseCase {
    val idStore = MockIdStore()
    val saveWorkout = MockSaveWorkout()
    val alreadyExist = MockWorkoutAlreadyExist(result = false)
    val addWorkout = AddWorkoutUseCase(
        workoutAlreadyExist = alreadyExist,
        saveWorkout = saveWorkout,
        idStore = idStore
    )
    return addWorkout
}