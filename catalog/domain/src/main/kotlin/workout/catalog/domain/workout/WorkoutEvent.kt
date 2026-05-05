package workout.catalog.domain.workout

sealed class WorkoutEvent(val workoutId: WorkoutId) {
    class Added(workoutId: WorkoutId) : WorkoutEvent(workoutId)
}
