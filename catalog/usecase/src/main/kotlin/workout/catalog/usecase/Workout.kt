package workout.catalog.usecase.workout

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import workout.catalog.usecase.MockIdStore
import workout.catalog.usecase.MockWorkoutAlreadyExist
import workout.catalog.usecase.WorkoutEvent
import workout.catalog.usecase.WorkoutId
import workout.catalog.usecase.WorkoutStatus

open class Workout(
    val status: WorkoutStatus,
    val id: WorkoutId,
    val taskText: String
) {
    private lateinit var event: WorkoutEvent

    fun popEvents(): List<WorkoutEvent> {
        val workoutId = 1
        return listOf(event)
    }

    fun addEvent(event: WorkoutEvent) {
        val workoutId = 1
        this.event = WorkoutEvent.Added(id)
    }

    companion object {
        fun add(
            idStore: MockIdStore,
            workoutAlreadyExist: MockWorkoutAlreadyExist,
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
