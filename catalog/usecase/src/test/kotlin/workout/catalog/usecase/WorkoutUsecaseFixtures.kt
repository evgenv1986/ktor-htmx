package workout.catalog.usecase

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