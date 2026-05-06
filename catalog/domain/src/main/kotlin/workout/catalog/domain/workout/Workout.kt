package workout.catalog.domain.workout

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure

open class Workout(
    val status: WorkoutStatus,
    val id: WorkoutId,
    val exercises: List<Exercise>
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
            exercises: List<Exercise>
        ) : Either<WorkoutError, Workout> = either {
            ensure(exercises.size != 0){
                WorkoutError.EmptyWorkout
            }
            ensure (!workoutAlreadyExist(exercises)){
                WorkoutError.AlreadyExist
            }
            val id = idStore.generate()
            Workout(
                status = WorkoutStatus.ADDED,
                id = id,
                exercises = exercises
            ).apply{ addEvent(WorkoutEvent.Added(id)) }
        }
    }

}
sealed interface WorkoutError {
    object AlreadyExist : WorkoutError
    object EmptyWorkout: WorkoutError
}
