package workout.catalog.domain

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure

open class Workout(
    val status: WorkoutStatus,
    val id: WorkoutId,
    val exercises: List<Exercise>
) {
    private var events = ArrayList<DomainEvent>()

    fun popEvents(): List<DomainEvent> {
        val res = events
        events = ArrayList()
        events.clear()
        return res
    }

    fun addEvent(event: DomainEvent) {
        events.add(event)
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
