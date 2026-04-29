package workout.catalog.usecase

sealed class WorkoutEvent(val workoutId: WorkoutId) {
    class Added(workoutId: WorkoutId) : WorkoutEvent(workoutId)
}
