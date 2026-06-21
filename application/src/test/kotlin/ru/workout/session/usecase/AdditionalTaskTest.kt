package ru.workout.session.usecase

import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import ru.workout.session.domain.additional.AdditionalTask
import ru.workout.session.domain.additional.AdditionalTaskError
import ru.workout.session.domain.additional.AdditionalTaskEvents
import ru.workout.session.domain.additional.AdditionalTaskStatus
import ru.workout.session.domain.additional.Rep
import ru.workout.session.domain.additional.Reps
import ru.workout.session.domain.additional.TaskProgressStatus

class AdditionalTaskTest: StringSpec({
    "created task should be in planned state"{
        val task = taskHandstand()
        task.status() shouldBe AdditionalTaskStatus.PLANNED
    }
    "created task has no completed repeats"{
        val task = taskHandstand()
        task.repsCompleted() shouldBe 0
    }
    "created task should progress not started"{
        val task = taskHandstand()
        task.progress() shouldBe TaskProgressStatus.NOT_STARTED
    }
    "completion repetitions increases task progress"{
        val task = taskHandstand()
        task.completeReps(Rep(30))
        task.progress() shouldBe TaskProgressStatus.IN_PROGRESS
    }
    "reps completed reaches reps target, progress state should be done"{
        val task = taskHandstand(targetReps = 300)
        task.completeReps(Rep(300))
        task.progress() shouldBe TaskProgressStatus.DONE
    }
    "planned task can beginning"{
        val task = taskHandstand()
        task.begin()
        task.popEvents().last()
            .shouldBeInstanceOf<AdditionalTaskEvents.TaskBeginningEvent>()
    }
    "beginning task should in progress state"{
        val task = taskHandstand()
        task.begin()
        task.status() shouldBe AdditionalTaskStatus.IN_PROGRESS
    }
    "task can cancelling in planned state"{
        val task = taskHandstand()
        task.cancel()
        task.popEvents().last()
            .shouldBeInstanceOf<AdditionalTaskEvents.TaskCancelledEvent>()
        task.status() shouldBe AdditionalTaskStatus.CANCELLED
    }
    "task can cancelling in progress state"{
        val task = taskHandstand()
        task.begin()
        task.cancel()
        task.popEvents().last()
            .shouldBeInstanceOf<AdditionalTaskEvents.TaskCancelledEvent>()
        task.status() shouldBe AdditionalTaskStatus.CANCELLED
    }
    "task completed reps is reached by target reps then task completion"{
        val task = taskBeginned(targetReps = 30)
        task.status() shouldBe AdditionalTaskStatus.IN_PROGRESS
        task.completeReps(Rep(30))
        task.popEvents().last().shouldBeInstanceOf<
                AdditionalTaskEvents.TaskCompletedEvent>()
        task.status() shouldBe AdditionalTaskStatus.COMPLETED
        task.progress() shouldBe TaskProgressStatus.DONE
    }
    "task in progress state " +
    "and has been completed reps " +
    "can be completed manually"{
        val task = taskInProgressWithRepsCompleted()
        task.complete()
        val eventCompletion = task.popEvents().last()
        eventCompletion.shouldBeInstanceOf<AdditionalTaskEvents.TaskCompletedEvent>()
        task.status() shouldBe AdditionalTaskStatus.COMPLETED
        task.progress() shouldBe TaskProgressStatus.IN_PROGRESS
    }



    "can add completed reps for task in progress state"{
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
    "can not complete rep for task in completed status"{
        val task = taskHandstand(targetReps = 30)
        task.completeReps(Rep(30))
        val completionResult = task.completeReps(Rep(10))
        val errorType = completionResult.shouldBeLeft()
        errorType.shouldBeInstanceOf<
                AdditionalTaskError.CompleteRepsOfTaskCompleted>()
        task.repsCompleted() shouldBe (30)
    }
    "can not complete rep for task in cancelled status"{
        val task = taskHandstand(targetReps = 30)
        task.completeReps(Rep(30))
        val completionResult = task.completeReps(Rep(10))
        val errorType = completionResult.shouldBeLeft()
        errorType.shouldBeInstanceOf<
                AdditionalTaskError.CompleteRepsOfCancelledTask>()
        task.repsCompleted() shouldBe (30)
    }
    "can cancel task in progress state or in planned state"{
        val task = taskHandstand(targetReps = 30)
        task.status() shouldBe AdditionalTaskStatus.PLANNED
        task.cancel()
        task.status() shouldBe AdditionalTaskStatus.CANCELLED
        task.popEvents().last()
            .shouldBeInstanceOf<AdditionalTaskEvents.TaskCancelledEvent>()
    }
    " Automatically complete the task when " +
        "the completed repetitions reach the target repetitions"{

    }










    "can planned additional task"{
        val handstandTask = AdditionalTask(
            taskId = "task-1",
            exerciseName = "handstand",
            repsTarget = Rep(300),
            repsCompleted = Reps(mutableListOf<Rep>())
        )
        handstandTask.status() shouldBe AdditionalTaskStatus.PLANNED
    }
    "can not beginning task in planned status"{
        val taskHandstand = taskHandstand(
//            status = AdditionalTaskStatus.PLANNED
        )
//        val result = taskHandstand.completeStep(
//            AdditionalStep(
//                stepId = "step1",
//                actualReps = 14,
//            )
//        )
//        result.shouldBeLeft()
    }

    "can beginning task in progress status"{
        val taskHandstand = taskHandstand(
//            status = AdditionalTaskStatus.IN_PROGRESS
        )
//        val result = taskHandstand.completeStep(
//            AdditionalStep(
//                stepId = "step1",
//                actualReps = 14,
//            )
//        )
//        result.shouldBeRight()
    }
    "can calc remain target time of task in progress status"{
        val taskHandstand = taskHandstand(
//            status = AdditionalTaskStatus.IN_PROGRESS,
            targetReps = 30)
//        taskHandstand.completeStep(
//            AdditionalStep(
//                stepId = "step1",
//                actualReps = 14,
//            )
//        ).shouldBeRight()
//        taskHandstand.remainsCompleted() shouldBe 30-14
    }

    "task complete after completing all reps by task"{
        val taskHandstand = taskHandstand(
//            status = AdditionalTaskStatus.IN_PROGRESS,
            targetReps = 30)
//        taskHandstand.completeStep(
//            AdditionalStep(
//                stepId = "step1",
//                actualReps = 30,
//            )
//        )
//        taskHandstand.status() shouldBe AdditionalTaskStatus.COMPLETED
    }

    "proposed execution quantity for next step"{
//        val task = taskWithFirstStepCompleted(actualTime = 30)
//            task.proposedQuantity() shouldBe 28
    }

    "думаю сессию делать отдельно, в другом классе теста. могу посмотреть количество оставшегося времени выполнения упражнения - через сессию"{
//        val handStand = taskHandstand(targetTime = 300)
//        handStand.remainsCompleted() shouldBe 300
//        handStand.status() shouldBe AdditionalTaskStatus.PLANNED

//        val session = AdditionalSession(
//            tasks = listOf(taskHandstand()),
//            status = SessionStatus.IN_PROGRESS
//        )
//        session.completeStep(
//            taskId = handstand
//        )
    }
})


