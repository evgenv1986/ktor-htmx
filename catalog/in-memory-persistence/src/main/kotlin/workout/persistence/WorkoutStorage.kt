package workout.persistence

import ru.workout.catalog.usecase.workout.SaveWorkout
import workout.common.event.DomainEventPublisher
import workout.persistence.catalog.domain.workout.Workout
import workout.persistence.catalog.domain.workout.WorkoutId

open class WorkoutStorage(val publisher: DomainEventPublisher) : SaveWorkout {
    internal val storage = LinkedHashMap<WorkoutId, Workout>()

    override fun save(workout: Workout) {
        publisher.publish(workout.popEvents())
        storage[workout.id] = workout
    }


}