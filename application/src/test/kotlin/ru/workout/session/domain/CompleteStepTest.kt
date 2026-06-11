package ru.workout.session.domain

import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlin.collections.set

class CompleteStepTest: StringSpec({

    "step can completed with actual reps"{
        val session: WorkoutSession = activeSession(steps = listOf("push-ups"))
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
        val result = session.completeStep(
            stepId = "s1",
            actualReps = 20
        )
        result
            .shouldBeLeft()
            .shouldBeInstanceOf<SessionError.StateIsNotInProgress>()
    }
    "can not complete step when session is already completed"{
        val session: WorkoutSession = session(status = SessionStatus.COMPLETED)
        val stepId = "s1"
        val actualReps = 20
        val step = step(
            stepId = stepId,
            actualReps = actualReps,
            status = StepStatus.PLANNED
        )
        val result = session.completeStep(stepId, actualReps)
        result
            .shouldBeLeft()
            .shouldBeInstanceOf<SessionError.StateIsNotInProgress>()
    }
    "can not complete step twice"{
        val session: WorkoutSession = activeSession(
            steps = listOf(step()),
            status = SessionStatus.IN_PROGRESS
        )
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
//            .shouldBeInstanceOf<SessionError.StepAlreadyCompletedError>()
    }
})



fun activeSession(
    steps: List<SessionStep> = listOf(step())
): WorkoutSession {
    return WorkoutSession(status = SessionStatus.IN_PROGRESS)
//    return session(
//        routine = SessionRoutine(123, steps),
//        status = SessionStatus.IN_PROGRESS
//    )
}

