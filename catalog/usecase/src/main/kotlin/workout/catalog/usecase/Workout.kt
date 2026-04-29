package workout.catalog.usecase.workout

import workout.catalog.usecase.MockIdStore
import workout.catalog.usecase.WorkoutEvents
import workout.catalog.usecase.WorkoutId
import workout.catalog.usecase.WorkoutStatus

open class Workout(
    val status: WorkoutStatus,
    val id: WorkoutId
) {
    private lateinit var event: WorkoutEvents.Added

    fun popEvents(): List<WorkoutEvents> {
        val workoutId = 1
        return listOf(event)
    }

    fun addEvent() {
        val workoutId = 1
        event = WorkoutEvents.Added(id)
    }

    companion object {
        fun add(idStore: MockIdStore): Workout {
            val id = idStore.generate()
            return Workout(
                status = WorkoutStatus.ADDED,
                id = id
            ).apply{ addEvent(
                //        ...event: DomainEvent
            )}
        }
    }

}