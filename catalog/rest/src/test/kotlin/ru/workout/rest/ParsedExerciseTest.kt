package ru.workout.rest

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import ru.workout.rest.exercise.ParsedExercise
import workout.catalog.domain.Exercise

class ParsedExerciseTest: StringSpec({
    "should extract name from simple string"{
        ParsedExercise("""
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
        exercise.equals(Exercise("Подтягивания"))
    }
})