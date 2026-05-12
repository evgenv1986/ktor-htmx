package workout.persistence.testFixtures

import workout.persistence.catalog.domain.workout.Exercise
import workout.persistence.catalog.domain.workout.Workout
import workout.persistence.catalog.domain.workout.WorkoutEvent
import workout.persistence.catalog.domain.workout.WorkoutId
import workout.persistence.catalog.domain.workout.WorkoutStatus

fun workoutWithStatusAdd(): Workout {
    val workout = Workout(
    WorkoutStatus.ADDED,
    WorkoutId(1),
    listOf(Exercise("pullUps"))
    )
    workout.addEvent(WorkoutEvent.Added(workout.id))
    return workout
}