package workout.catalog.domain

fun workoutWithStatusAdd(): Workout {
    val workout = Workout(
    WorkoutStatus.ADDED,
    WorkoutId(1),
    listOf(Exercise("pullUps"))
    )
    workout.addEvent(WorkoutEvent.Added(workout.id))
    return workout
}