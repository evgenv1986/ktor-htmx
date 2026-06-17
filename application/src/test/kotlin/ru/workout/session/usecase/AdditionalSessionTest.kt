package ru.workout.session.usecase

import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import ru.workout.common.event.DomainEvent
import ru.workout.session.domain.SessionStatus
import ru.workout.session.domain.StepEvents
import ru.workout.session.usecase.additional.AdditionalSession
import ru.workout.session.usecase.additional.AdditionalStep
import ru.workout.session.usecase.additional.AdditionalTask
import ru.workout.session.usecase.additional.AdditionalTaskStatus

class AdditionalSessionTest: StringSpec({
//    "думаю сессию делать отдельно, в другом классе теста.
    //    могу посмотреть количество оставшегося времени выполнения упражнения - через сессию"
    "can complete step" {
//        val handStand = taskHandstand(targetTime = 300)
//        handStand.remainsCompleted() shouldBe 300
//        handStand.status() shouldBe AdditionalTaskStatus.PLANNED

        val session = AdditionalSession(
            tasks = listOf(taskHandstand()),
            status = SessionStatus.IN_PROGRESS
        )
        val taskId = "t1"
        val stepId = "s1"
        val step = AdditionalStep(
            stepId = stepId,
            actualTime = 14,
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
})