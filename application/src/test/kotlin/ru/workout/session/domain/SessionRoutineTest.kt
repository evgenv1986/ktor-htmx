package ru.workout.session.domain

import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

class SessionRoutineTest: StringSpec ({
    "routine create with exercises"{
        val routine = SessionRoutine(
            planId = 123,
            exercises = listOf("Приседания", "Жим лёжа")
        )
        val result = SessionRoutine.from(
            123,
            listOf("Приседания", "Жим лёжа")).shouldBeRight()
        result.planId shouldBe 123
        result.exercises shouldBe listOf("Приседания", "Жим лёжа")
    }
    "routine can not be empty"{
        val routine = SessionRoutine.from(1, listOf()).shouldBeLeft()
        routine.shouldBeInstanceOf<SessionRoutineError.EmptyExercises>()
    }
})

