package ru.workout.session.domain

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

class WorkoutSessionTest: StringSpec({
    "session created from routine"{
        val routine = SessionRoutine(123, listOf("Приседания","Подтягивания"))
        val session = WorkoutSession.prepare(routine, 1)
        session.status == "planned"
        session.routine == routine
    }
    "session creation produce event"{
        val routine = SessionRoutine(123, listOf("Приседания","Подтягивания"))
        val sessionId = 1
        val session = WorkoutSession.prepare(routine, sessionId)
        val events = session.popEvents()
        events.count() shouldBe 1
        val event = events[0].shouldBeInstanceOf<SessionEvents.SessionCreatedEvent>()
        event.sessionId shouldBe 1
    }
})