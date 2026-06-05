package ru.workout.session.domain

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import java.time.OffsetDateTime

class WorkoutSessionTest: StringSpec({
    "session created from routine"{
        val routine = SessionRoutine(123, listOf("Приседания","Подтягивания"))
        val session = WorkoutSession.prepare(routine, 1)
        session.status == SessionStatus.PREPARED
        session.routine == routine
    }
    "session creation produce event"{
        val routine = SessionRoutine(123, listOf("Приседания","Подтягивания"))
        val sessionId = 1
        val session = WorkoutSession.prepare(routine, sessionId)
        val events = session.popEvents()
        events.count() shouldBe 1
        val event = events[0].shouldBeInstanceOf<SessionEvents.SessionPreparedEvent>()
        event.sessionId shouldBe 1
    }
    "prepared session can be begun"{
        val routine = SessionRoutine(123, listOf("Приседания","Подтягивания"))
        val sessionId = 1
        val startedAt = OffsetDateTime.parse("2026-06-05T10:00:00+00:00")
        val session = WorkoutSession(routine, sessionId, SessionStatus.PREPARED)
        session.begin(startedAt = startedAt)
        session.status shouldBe SessionStatus.IN_PROGRESS
        val events = session.popEvents()
        val event = events[0].shouldBeInstanceOf<SessionEvents.InProgress>()
        event.sessionId shouldBe 1
        event.startedAt shouldBe startedAt
    }
})