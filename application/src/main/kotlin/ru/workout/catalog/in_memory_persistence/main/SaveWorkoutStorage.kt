package ru.workout.catalog.in_memory_persistence.main

import ru.workout.catalog.domain.Workout
import ru.workout.catalog.domain.WorkoutId
import ru.workout.catalog.usecase.SaveWorkout
import ru.workout.common.event.DomainEventPublisher

open class SaveWorkoutStorage(val publisher: DomainEventPublisher) : SaveWorkout {
    val storage = LinkedHashMap<WorkoutId, Workout>()
    override fun save(workout: Workout) {
        publisher.publish(workout.popEvents())
        storage[workout.id] = workout
    }


}