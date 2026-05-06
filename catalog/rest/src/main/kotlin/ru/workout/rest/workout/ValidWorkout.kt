package ru.workout.rest.workout

import ru.workout.rest.exercise.ParsedExercise
import workout.catalog.domain.workout.Exercise

open class ValidWorkout(val input: WorkoutInput) {
    fun exercises(): List<Exercise>{
        return input.workoutText.lines()
            .filter { it.isNotBlank() }
            .map { line -> ParsedExercise(line).toExercise() }
    }
}