package ru.workout.session.usecase

import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import ru.workout.session.domain.additional.AdditionalTask
import ru.workout.session.domain.additional.AdditionalTaskError
import ru.workout.session.domain.additional.AdditionalTaskEvents
import ru.workout.session.domain.additional.AdditionalTaskStatus
import ru.workout.session.domain.additional.Rep
import ru.workout.session.domain.additional.Reps
import ru.workout.session.domain.additional.TaskProgressStatus
import ru.workout.session.domain.additional.TaskStatus

class AdditionalTaskTest: StringSpec({
    "created task should be in planned state"{
        val task = taskHandstand()
        task.status() shouldBe TaskStatus.Planned
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

    "task can cancelling in planned state"{
        val task = taskHandstand()
        task.cancel()
        task.popEvents().last()
            .shouldBeInstanceOf<AdditionalTaskEvents.TaskCancelledEvent>()
        task.status() shouldBe AdditionalTaskStatus.CANCELLED
    }
    "task can cancelling in active state"{
        val task = taskHandstand()
        task.cancel()
        task.popEvents().last()
            .shouldBeInstanceOf<AdditionalTaskEvents.TaskCancelledEvent>()
        task.status() shouldBe AdditionalTaskStatus.CANCELLED
    }
    "task completed reps is reached by target reps then task completion and progress is done"{
        val task = taskActive(repsTarget = 30)
        task.status() shouldBe AdditionalTaskStatus.ACTIVE
        task.completeReps(Rep(30))
        task.popEvents().last().shouldBeInstanceOf<
                AdditionalTaskEvents.TaskCompletedEvent>()
        task.status() shouldBe AdditionalTaskStatus.COMPLETED
        task.progress() shouldBe TaskProgressStatus.DONE
    }
    "task in active state " +
        "and has been completed reps " +
        "can be completed manually"{
            val task = taskActive(
                repsTarget = 300,
                repsCompleted = 10
            )
            task.complete()
            val eventCompletion = task.popEvents().last()
            eventCompletion.shouldBeInstanceOf<AdditionalTaskEvents.TaskCompletedEvent>()
            task.status() shouldBe AdditionalTaskStatus.COMPLETED
            task.progress() shouldBe TaskProgressStatus.IN_PROGRESS
            task.repsRemaining() shouldBe Rep(300 - 10)
        }
    "task in planned state and has not been completed reps can be completed manually"{
        val task = taskInPlanned()
        task.complete()
        val eventCompletion = task.popEvents().last()
        eventCompletion.shouldBeInstanceOf<AdditionalTaskEvents.TaskCompletedEvent>()
        task.status() shouldBe AdditionalTaskStatus.COMPLETED
        task.progress() shouldBe TaskProgressStatus.NOT_STARTED
    }
    "can not cancelled completed task"{
        val task = taskCompleted()
        task.status() shouldBe AdditionalTaskStatus.COMPLETED
        val result = task.cancel()
        val error = result.shouldBeLeft()
        error.shouldBeInstanceOf<
                AdditionalTaskError.TaskIsCompleted>()
    }
    "cancelled task cannot complete reps"{
        val task = taskHandstand()
        task.cancel()
        val result = task.completeReps(Rep(10))
        val error = result.shouldBeLeft()
        error.shouldBeInstanceOf<
                AdditionalTaskError.TaskIsCancelled>()
    }
    "can not complete reps in completed task state"{
        val task = taskCompleted()
        val result = task.completeReps(Rep(10))
        val error = result.shouldBeLeft()
        error.shouldBeInstanceOf<
                AdditionalTaskError.TaskIsCompleted>()
    }
    "the amount of completed repetitions reached the target reps"{
        val repsComleted = Reps(mutableListOf<Rep>(Rep(2)))
        val targetReps = Rep(5)
        repsComleted.add(Rep(3))
        repsComleted.isReachedBy(targetReps).shouldBeTrue()
    }
    "planned task becomes active after first completed reps"{
        val task = taskInPlanned()
        task.status() == TaskStatus.Planned
        task.completeReps(Rep(30))
        task.status() == TaskStatus.Active() // shouldBe TaskStatus2.Active()
    }
    "planned task becomes completed after first completed reps their reaches target reps"{
        val task = taskInPlanned(targetReps = 30)
        task.completeReps(Rep(30))
        task.status() == TaskStatus.Completed()
    }

    "completed reps are accumulated"{}
    "task becomes completed when target reps are reached"{}
    "task in active state can be completed before target reps reached"{}
    "task in completed state can not complete reps"{}
    "task progress should be done reps reaches target reps"{}


    "can complete reps for task in progress state"{
        val taskId = "task1"
        val task: AdditionalTask = taskInPlanned(
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
        val task: AdditionalTask = taskInPlanned()
        task.completeReps(Rep(10))
        task.status() shouldBe AdditionalTaskStatus.ACTIVE
    }
    "task with fully completed target reps must have status completed"{
        val task: AdditionalTask = taskInPlanned(targetReps = 30)
        task.completeReps(Rep(30))
        task.status() shouldBe AdditionalTaskStatus.COMPLETED
    }
    "in progress task should return remaining reps"{
        val task: AdditionalTask = taskInPlanned(targetReps = 30)
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
                AdditionalTaskError.TaskIsCompleted>()
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

    " Automatically complete the task when " +
        "the completed repetitions reach the target repetitions"{

    }
    "proposed execution quantity for next step"{
        val task = taskActive()
            task.proposedQuantity() shouldBe 28
    }
})

