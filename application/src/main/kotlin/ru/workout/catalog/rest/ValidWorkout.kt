package ru.workout.catalog.rest

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import ru.workout.catalog.domain.TaskExercise
import ru.workout.rest.exercise.ParsedExercise
import ru.workout.session.domain.WorkoutPlan

open class ValidWorkout(val input: WorkoutPlan, val field: String = "exercises") {
    fun exercises(): Either<ValidationError, List<TaskExercise>> = either {
        val exercises = input.exercises
            .filter { it.isNotBlank() }
            .map { line -> ParsedExercise(line).toExercise() }
        ensure(exercises.isNotEmpty()){
            ValidationError(field, "Тренировка должна быть заполнена упражнениями")
        }
        exercises
    }
}

data class ValidationError(val field: String, val message: String)
