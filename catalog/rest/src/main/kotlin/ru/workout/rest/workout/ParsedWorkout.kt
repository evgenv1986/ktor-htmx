package ru.workout.rest.workout

import ru.workout.rest.exercise.ParsedExercise
import workout.catalog.domain.workout.Exercise

open class ParsedWorkout {
    fun parse(text: String): List<Exercise>{
        return text.lines()
            .filter { it.isNotBlank() }
            .map { line -> ParsedExercise(line).toExercise() }
    }

}