package ru.workout.session.usecase

import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import ru.workout.session.domain.SessionStatus
import ru.workout.session.usecase.additional.AdditionalSession
import ru.workout.session.usecase.additional.AdditionalStep
import ru.workout.session.usecase.additional.AdditionalTask
import ru.workout.session.usecase.additional.AdditionalTaskStatus

class AdditionalTaskTest: StringSpec({
    "can planned additional task"{
        val handstandTask = AdditionalTask(
            taskId = "task-1",
            exerciseName = "стойка на руках",
            targetTime = 300,
            steps = mutableListOf<AdditionalStep>(),
            status = AdditionalTaskStatus.PLANNED
        )
        handstandTask.status() shouldBe AdditionalTaskStatus.PLANNED
    }
    "can not beginning task in planned status"{
        val taskHandstand = taskHandstand(status = AdditionalTaskStatus.PLANNED)
        val result = taskHandstand.completeStep(
            AdditionalStep(
                stepId = "step1",
                actualTime = 14,
            )
        )
        result.shouldBeLeft()
    }

    "can beginning task in progress status"{
        val taskHandstand = taskHandstand(status = AdditionalTaskStatus.IN_PROGRESS)
        val result = taskHandstand.completeStep(
            AdditionalStep(
                stepId = "step1",
                actualTime = 14,
            )
        )
        result.shouldBeRight()
    }
    "can calc remain target time of task in progress status"{
        val taskHandstand = taskHandstand(
            status = AdditionalTaskStatus.IN_PROGRESS,
            targetTime = 30)
        taskHandstand.completeStep(
            AdditionalStep(
                stepId = "step1",
                actualTime = 14,
            )
        ).shouldBeRight()
        taskHandstand.remainsCompleted() shouldBe 30-14
    }

    "task complete after completing all reps by task"{
        val taskHandstand = taskHandstand(
            status = AdditionalTaskStatus.IN_PROGRESS,
            targetTime = 30)
        taskHandstand.completeStep(
            AdditionalStep(
                stepId = "step1",
                actualTime = 30,
            )
        )
        taskHandstand.status() shouldBe AdditionalTaskStatus.COMPLETED
    }

    "proposed execution quantity for next step"{
        val task = taskWithFirstStepCompleted(actualTime = 30)
            task.proposedQuantity() shouldBe 28
    }

    "думаю сессию делать отдельно, в другом классе теста. могу посмотреть количество оставшегося времени выполнения упражнения - через сессию"{
        val handStand = taskHandstand(targetTime = 300)
        handStand.remainsCompleted() shouldBe 300
        handStand.status() shouldBe AdditionalTaskStatus.PLANNED

        val session = AdditionalSession(
            tasks = listOf(taskHandstand()),
            status = SessionStatus.IN_PROGRESS
        )
//        session.completeStep(
//            taskId = handstand
//        )
    }
})


