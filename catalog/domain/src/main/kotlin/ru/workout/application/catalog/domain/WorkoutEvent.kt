package ru.workout.application.catalog.domain

import ru.workout.application.common.event.DomainEvent


sealed class WorkoutEvent(val workoutId: WorkoutId): DomainEvent {
    class Added(workoutId: WorkoutId) : WorkoutEvent(workoutId)
}
