package ru.workout.rest.workout

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class ParsedWorkoutTest: StringSpec({
    "should parse workout text with two exercises"{
        val input =  """
            1. Подтягивания
            2. Отжимания
        """.trimIndent()
        val exercises = ParsedWorkout().parse(input)
        exercises.size shouldBe 2
        exercises[0].name() shouldBe "Подтягивания"
        exercises[1].name() shouldBe "Отжимания"
    }
})

