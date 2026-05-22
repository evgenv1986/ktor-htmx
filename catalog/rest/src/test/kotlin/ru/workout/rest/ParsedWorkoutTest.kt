//package ru.workout.rest
//
//import io.kotest.assertions.arrow.core.shouldBeLeft
//import io.kotest.assertions.arrow.core.shouldBeRight
//import io.kotest.core.spec.style.StringSpec
//import io.kotest.matchers.shouldBe
//import ru.workout.rest.workout.ValidWorkout
//import ru.workout.rest.workout.WorkoutInput
//
//class ParsedWorkoutTest: StringSpec({
//    "should parse workout text with two exercises"{
//        val input = WorkoutInput(
//            """
//            1. Подтягивания
//            2. Отжимания
//            """.trimIndent())
//        val exercises = ValidWorkout(input).exercises().shouldBeRight()
//        exercises.size shouldBe 2
//        exercises[0].name() shouldBe "Подтягивания"
//        exercises[1].name() shouldBe "Отжимания"
//    }
//    "should parse error on invalid text exercises"{
//        val input = WorkoutInput(
//            """
//            1
//            2.
//            """.trimIndent())
//        val result = ValidWorkout(input).exercises().shouldBeLeft()
//    }
//})