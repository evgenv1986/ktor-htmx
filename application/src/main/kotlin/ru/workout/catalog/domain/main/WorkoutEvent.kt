package ru.workout.catalog.domain

import ru.workout.common.event.DomainEvent


sealed class WorkoutEvent(val workoutId: WorkoutId): DomainEvent {
    class Added(workoutId: WorkoutId) : WorkoutEvent(workoutId)
}
