package ru.workout.catalog.rest.exercise.test

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import ru.workout.catalog.domain.TaskExercise
import ru.workout.rest.exercise.ParsedExercise

class ParsedExerciseTest: StringSpec({
    "should extract name from simple string"{
        ParsedExercise(
            """
                1. Подтягивания
            """.trimIndent()
        ).equals(ParsedExercise("Подтягивания"))
    }
    "should create exercise from parsed exercise"{
        val exercise = ParsedExercise("""
                1. Подтягивания
            """.trimIndent()
        ).toExercise()
        exercise.name() shouldBe "Подтягивания"
        exercise.equals(TaskExercise("Подтягивания"))
    }
})