package ru.workout.persistence

import ru.workout.catalog.usecase.workout.SaveWorkout
import ru.workout.common.event.DomainEventPublisher
import ru.workout.catalog.domain.Workout
import ru.workout.catalog.domain.WorkoutId

open class SaveWorkoutStorage(val publisher: DomainEventPublisher) : SaveWorkout {
    val storage = LinkedHashMap<WorkoutId, Workout>()
    override fun save(workout: Workout) {
        publisher.publish(workout.popEvents())
        storage[workout.id] = workout
    }


}