package workout.catalog.usecase

import workout.catalog.usecase.AddWorkoutUseCase
import workout.catalog.usecase.MockIdStore
import workout.catalog.usecase.MockSaveWorkout
import workout.catalog.usecase.MockWorkoutAlreadyExist

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