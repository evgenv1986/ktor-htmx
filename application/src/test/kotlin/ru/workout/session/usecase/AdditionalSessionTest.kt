package ru.workout.session.usecase

import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import ru.workout.common.event.DomainEvent
import ru.workout.session.domain.SessionStatus
import ru.workout.session.domain.StepEvents
import ru.workout.session.domain.additional.AdditionalSession
import ru.workout.session.domain.additional.AdditionalStep
import ru.workout.session.domain.additional.AdditionalTaskStatus

class AdditionalSessionTest: StringSpec({
//    "думаю сессию делать отдельно, в другом классе теста.
    //    могу посмотреть количество оставшегося времени выполнения упражнения - через сессию"
    "can complete step" {
        val session = AdditionalSession(
            tasks = listOf(taskHandstand()),
            status = SessionStatus.IN_PROGRESS
        )
        val taskId = "t1"
        val stepId = "s1"
        val step = AdditionalStep(
            stepId = stepId,
            actualReps = 14,
        )
        val task = taskHandstand(status = AdditionalTaskStatus.IN_PROGRESS)
        session.completeStep(
            step = step,
            task = task
        )
        val events: List<DomainEvent> = session.popEvents()
        val stepCompleted = events.first()
        stepCompleted.shouldBeInstanceOf<StepEvents.StepCompletedEvent>()
    }
    "can not complete step in task status is planned or other (not in progress)"{
        val session = additionalSession(status = SessionStatus.IN_PROGRESS)
        val task = taskHandstand(status = AdditionalTaskStatus.PLANNED)
        val step = step()

        val result = session.completeStep(step, task)

        result.shouldBeLeft()
    }
    "session status should be completed after task completed"{
        val session = additionalSession(status = SessionStatus.IN_PROGRESS)
        val task = taskHandstand(
            status = AdditionalTaskStatus.IN_PROGRESS,
            targetTime = 30)
        val step = step(actualTime = 30)

        session.completeStep(step, task)

        val events  = session.popEvents()
        val sessionCompleted = events.first()
        sessionCompleted.shouldBeInstanceOf<StepEvents.StepCompletedEvent>()
        session.status() shouldBe SessionStatus.COMPLETED
    }
})