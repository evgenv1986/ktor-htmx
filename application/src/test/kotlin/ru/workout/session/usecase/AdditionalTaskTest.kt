package ru.workout.session.usecase

import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import ru.workout.session.domain.SessionStatus
import ru.workout.session.domain.additional.AdditionalSession
import ru.workout.session.domain.additional.AdditionalStep
import ru.workout.session.domain.additional.AdditionalTask
import ru.workout.session.domain.additional.AdditionalTaskEvents
import ru.workout.session.domain.additional.AdditionalTaskStatus
import ru.workout.session.domain.additional.Rep
import ru.workout.session.domain.additional.Reps

class AdditionalTaskTest: StringSpec({
    "can add completed reps for task"{
        val taskId = "task1"
        val task: AdditionalTask = taskInProgress(
            "handstand",
            300,
            taskId
        )
        val repsCompleted = 14
        task.completeReps(Rep(repsCompleted))
        val repsCompletedEvent = task.popEvents().last()
        repsCompletedEvent.shouldBeInstanceOf<AdditionalTaskEvents.RepsCompletedEvent>()
        repsCompletedEvent.taskId shouldBe taskId
    }
    "the status of a partially completed task should be in progress"{
        val task: AdditionalTask = taskInProgress()
        task.completeReps(Rep(10))
        task.status() shouldBe AdditionalTaskStatus.IN_PROGRESS
    }
    "task with fully completed target reps must have status completed"{
        val task: AdditionalTask = taskInProgress(targetReps = 30)
        task.completeReps(Rep(30))
        task.status() shouldBe AdditionalTaskStatus.COMPLETED
    }
    "in progress task should return remaining reps"{
        val task: AdditionalTask = taskInProgress(targetReps = 30)
        task.completeReps(Rep(10))
        var remain = task.repsRemaining() shouldBe Rep(30-10)
        remain.intValue() shouldBe 30-10
    }

    "can planned additional task"{
        val handstandTask = AdditionalTask(
            taskId = "task-1",
            exerciseName = "стойка на руках",
            targetReps = Rep(300),
            steps = mutableListOf<AdditionalStep>(),
            completedReps = Reps(mutableListOf<Rep>())
        )
        handstandTask.status() shouldBe AdditionalTaskStatus.PLANNED
    }
    "can not beginning task in planned status"{
        val taskHandstand = taskHandstand(status = AdditionalTaskStatus.PLANNED)
        val result = taskHandstand.completeStep(
            AdditionalStep(
                stepId = "step1",
                actualReps = 14,
            )
        )
        result.shouldBeLeft()
    }

    "can beginning task in progress status"{
        val taskHandstand = taskHandstand(status = AdditionalTaskStatus.IN_PROGRESS)
        val result = taskHandstand.completeStep(
            AdditionalStep(
                stepId = "step1",
                actualReps = 14,
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
                actualReps = 14,
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
                actualReps = 30,
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


