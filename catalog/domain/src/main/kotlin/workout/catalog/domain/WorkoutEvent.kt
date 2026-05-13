package workout.catalog.domain

sealed class WorkoutEvent(val workoutId: WorkoutId): DomainEvent {
    class Added(workoutId: WorkoutId) : WorkoutEvent(workoutId)
}
