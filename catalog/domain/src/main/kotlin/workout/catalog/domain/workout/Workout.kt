package workout.catalog.domain.workout

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure

open class Workout(
    val status: WorkoutStatus,
    val id: WorkoutId,
    val taskText: String
) {
    private lateinit var event: WorkoutEvent

    fun popEvents(): List<WorkoutEvent> {
        return listOf(event)
    }

    fun addEvent(event: WorkoutEvent) {
        this.event = event
    }

    companion object {
        fun add(
            idStore: WorkoutIdStore,
            workoutAlreadyExist: WorkoutAlreadyExist,
            workoutText: String
        ) : Either<WorkoutError, Workout> = either {
            ensure(workoutText != ""){
                WorkoutError.EmptyWorkout
            }
            ensure (!workoutAlreadyExist(workoutText)){
                WorkoutError.AlreadyExist
            }
            val id = idStore.generate()
            Workout(
                status = WorkoutStatus.ADDED,
                id = id,
                taskText = workoutText
            ).apply{ addEvent(WorkoutEvent.Added(id)) }
        }
    }

}
sealed interface WorkoutError {
    object AlreadyExist : WorkoutError
    object EmptyWorkout: WorkoutError
}
