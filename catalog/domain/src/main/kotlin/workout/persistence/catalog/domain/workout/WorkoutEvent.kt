package workout.persistence.catalog.domain.workout

sealed class WorkoutEvent(val workoutId: WorkoutId): DomainEvent {
    class Added(workoutId: WorkoutId) : WorkoutEvent(workoutId)
}
