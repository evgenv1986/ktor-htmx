package workout.catalog.usecase.workout

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import workout.catalog.usecase.MockIdStore
import workout.catalog.usecase.MockWorkoutAlreadyExist
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
        fun add(
            idStore: MockIdStore,
            workoutAlreadyExist: MockWorkoutAlreadyExist,
            workoutText: String
        ) : Either<WorkoutError, Workout> = either {
            ensure (!workoutAlreadyExist(workoutText)){
                WorkoutError.AlreadyExist
            }
            val id = idStore.generate()
            Workout(
                status = WorkoutStatus.ADDED,
                id = id
            ).apply{ addEvent( // ...event: DomainEvent
            )}
        }
    }

}
interface WorkoutError {


    object AlreadyExist : WorkoutError
}
