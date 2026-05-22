package ru.workout.rest.workout

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import ru.workout.rest.exercise.ParsedExercise
import ru.workout.catalog.domain.TaskExercise

open class ValidWorkout(val input: WorkoutInput, val field: String = "exercises") {
    fun exercises(): Either<ValidationError, List<TaskExercise>> = either {
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
