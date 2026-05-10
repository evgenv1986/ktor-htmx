package ru.workout.rest.workout

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import ru.workout.rest.exercise.ParsedExercise
import workout.catalog.domain.workout.Exercise

open class ValidWorkout(val input: WorkoutInput, val field: String) {
    fun exercises(): Either<ValidationError, List<Exercise>> = either {
        val exercises = input.workoutText.lines()
            .filter { it.isNotBlank() }
            .map { line -> ParsedExercise(line).toExercise() }
        ensure(exercises.isNotEmpty()){
            ValidationError(field, "Тренировка должна быть заполнена упражнениями")
        }
        exercises
    }
}

data class ValidationError(val field: String, val message: String)
