package ru.workout.persistence

//import ru.workout.catalog.usecase.workout.SaveWorkout

//open class SaveWorkoutStorage(val publisher: DomainEventPublisher) : SaveWorkout {
//    val storage = LinkedHashMap<WorkoutId, Workout>()
//    override fun save(workout: Workout) {
//        publisher.publish(workout.popEvents())
//        storage[workout.id] = workout
//    }
//
//
//}