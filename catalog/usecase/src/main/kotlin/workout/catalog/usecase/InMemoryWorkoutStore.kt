package workout.catalog.usecase

import arrow.core.Either
import arrow.core.raise.context.bind
import arrow.core.raise.either
import arrow.core.raise.ensureNotNull
import arrow.core.right
import workout.catalog.usecase.workout.Workout

interface RestoreWorkout {
    operator fun invoke(workoutText: String): Either<WorkoutStoreError, Workout>
}
open class InMemoryWorkoutStore(
    val data: MutableMap<WorkoutId, Workout>
): RestoreWorkout {
    override fun invoke(workoutText: String)
    : Either<WorkoutStoreError, Workout> = either {
        ensureNotNull( data.values.find { it.taskText == workoutText } ){
            WorkoutStoreError.NotFound
        }
    }

}

sealed interface WorkoutStoreError {
    object NotFound : WorkoutStoreError
}
