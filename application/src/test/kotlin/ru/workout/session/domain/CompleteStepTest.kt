package ru.workout.session.domain

import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

class CompleteStepTest: StringSpec({
    "step can completed with actual reps"{
        val session: WorkoutSession = activeSession(exercises = listOf("push-ups"))
        session.completeStep(
            stepId = "s1",
            actualReps = 20
        )
        val event = session.popEvents().last()
        event shouldBe StepEvents.StepCompletedEvent(
            stepId = "s1",
            actualReps = 20,
            status = StepStatus.COMPLETED
        )
    }
    "can not complete step when session is not started"{
        val session: WorkoutSession = session(status = SessionStatus.PREPARED)
        session.completeStep(
            stepId = "s1",
            actualReps = 20
        )
        val result = session.completeStep(
            stepId = "s1",
            actualReps = 20
        )
        result
            .shouldBeLeft()
            .shouldBeInstanceOf<SessionError.StateIsNotInProgress>()
    }
})



fun activeSession(
    exercises: List<String> = listOf("push-ups")
): WorkoutSession {
    return session(
        routine = SessionRoutine(123, exercises),
        status = SessionStatus.IN_PROGRESS
    )
}

