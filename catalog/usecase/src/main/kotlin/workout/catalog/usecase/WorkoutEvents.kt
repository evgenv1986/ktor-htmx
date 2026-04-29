package workout.catalog.usecase

sealed class WorkoutEvents(val workoutId: WorkoutId) {
    class Added(workoutId: WorkoutId) : WorkoutEvents(workoutId)
}
