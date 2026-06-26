package ru.workout.session.usecase

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import ru.workout.session.domain.SessionStatus
import ru.workout.session.domain.SessionStep
import ru.workout.session.domain.StepEvents
import ru.workout.session.domain.StepStatus
import ru.workout.session.domain.WorkoutSession
import ru.workout.session.domain.additional.Rep
import ru.workout.session.domain.additional.Reps

class CompleteStepTest: StringSpec({
    "the amount of completed repetitions reached the target reps"{
        val repsCompleted = Reps(mutableListOf(Rep(2)))
        val targetReps = Rep(5)
        repsCompleted.add(Rep(3))
        repsCompleted.isReachedBy(targetReps).shouldBeTrue()
    }
    "completed reps are accumulated over multiple calls"{
        val repsCompleted = Reps(mutableListOf(Rep(2)))
        repsCompleted.add(Rep(8))
        repsCompleted
            .totalReps() shouldBe Reps(mutableListOf(Rep(8+2)))
            .totalReps()
    }

    //35. test_step_created_as_planned
    //    → Рождается Step(exerciseName, targetReps, status=PLANNED)
    //    → Шаг рождается запланированным
    //
    //36. test_step_has_no_actual_reps_when_planned
    //    → step.actualReps == null
    //    → Инвариант: нет результата до выполнения

    "создать сессию с одним шагом" +
    "create session with single step"{
        val session = session()
    }



    "can not complete step without stepTemplate"{
        val stepTemplate = mapOf("pull-ups" to 30)

//        val session: WorkoutSession = WorkoutSession.create(
//            stepTemplate,
//            SessionStatus.IN_PROGRESS
//        )

    }
    "step can completed with actual reps"{
        val session: WorkoutSession = WorkoutSession(

            stepsTemplate = mutableMapOf<String, SessionStep>(
                "st1" to SessionStep("st1", 10, StepStatus.PLANNED),
                "st2" to SessionStep("st2", 20, StepStatus.PLANNED),
                "st3" to SessionStep("st3", 30, StepStatus.PLANNED),
            ),
            SessionStatus.IN_PROGRESS
        )
        session.stepTemplatesCount() shouldBe 3

        session.completeStep("st1", 10)

        session.stepStatus("st1") shouldBe StepStatus.COMPLETED

        val event = session.popEvents().last()
        event shouldBe StepEvents.StepCompletedEvent(
            stepId = "st1",
            actualReps = 10,
            status = StepStatus.COMPLETED
        )
    }
    "can not complete step when session is not started"{
//        val session: WorkoutSession = session(status = SessionStatus.PREPARED)
//        val result = session.completeStep(
//            stepId = "s1",
//            actualReps = 20
//        )
//        result
//            .shouldBeLeft()
//            .shouldBeInstanceOf<SessionError.StateIsNotInProgress>()
    }
    "can not complete step when session is already completed"{
//        val session: WorkoutSession = session(status = SessionStatus.COMPLETED)
//        val stepId = "s1"
//        val actualReps = 20
//        val step = step(
//            stepId = stepId,
//            actualReps = actualReps,
//            status = StepStatus.PLANNED
//        )
//        val result = session.completeStep(stepId, actualReps)
//        result
//            .shouldBeLeft()
//            .shouldBeInstanceOf<SessionError.StateIsNotInProgress>()
    }
    "can not complete step twice"{
//        val session: WorkoutSession = activeSession(
//            steps = listOf(step()),
//            status = SessionStatus.IN_PROGRESS
//        )
//        session.completeStep(
//            stepId = "s1",
//            actualReps = 20
//        )
//        val result = session.completeStep(
//            stepId = "s1",
//            actualReps = 20
//        )
//        result
//            .shouldBeLeft()
//            .shouldBeInstanceOf<SessionError.StepAlreadyCompletedError>()
    }
})



//fun activeSession(
//    steps: List<SessionStep> = listOf(step())
//): WorkoutSession {
//    return WorkoutSession(status = SessionStatus.IN_PROGRESS)
//    return session(
//        routine = SessionRoutine(123, steps),
//        status = SessionStatus.IN_PROGRESS
//    )
//}

fun session(): WorkoutSession {
    return WorkoutSession(
        stepsTemplate = null,
        status = null
    )
}