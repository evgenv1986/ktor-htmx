package workout.persistence

import ru.workout.catalog.usecase.workout.SaveWorkout
import workout.common.event.DomainEventPublisher
import workout.catalog.domain.Workout
import workout.catalog.domain.WorkoutId

open class SaveWorkoutStorage(val publisher: DomainEventPublisher) : SaveWorkout {
    internal val storage = LinkedHashMap<WorkoutId, Workout>()

    override fun save(workout: Workout) {
        publisher.publish(workout.popEvents())
        storage[workout.id] = workout
    }


}