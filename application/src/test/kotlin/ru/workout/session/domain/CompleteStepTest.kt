package ru.workout.session.domain

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

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
})



fun activeSession(
    exercises: List<String> = listOf("push-ups")
): WorkoutSession {
    return session(
        routine = SessionRoutine(123, exercises))
}

