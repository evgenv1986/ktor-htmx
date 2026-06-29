package ru.workout.session.usecase

import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import ru.workout.session.domain.additional.AdditionalTaskError
import ru.workout.session.domain.additional.AdditionalTaskEvents
import ru.workout.session.domain.additional.Rep
import ru.workout.session.domain.additional.TaskProgressStatus
import ru.workout.session.domain.additional.TaskStatus

class AdditionalTaskTest: StringSpec({
    // Создание и начальное состояние
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

// Переход PLANNED → ACTIVE
    "completion repetitions increases task progress"{
        val task = taskHandstand()
        task.completeReps(Rep(30))
        task.progress() shouldBe TaskProgressStatus.IN_PROGRESS
    }
    "planned task becomes active after first completed reps"{
        val task = taskInPlanned()
        task.completeReps(Rep(30))
        task.status() shouldBe TaskStatus.Active
        val beginningEvent = task.popEvents().last()
        beginningEvent.shouldBeInstanceOf<AdditionalTaskEvents.TaskBeginningEvent>()
    }
    "can complete reps for task in progress state"{
        val taskId = "taskId1"
        val task = taskActive(taskId = taskId)
        task.completeReps(Rep(14))
        val completedEvent = task.popEvents().first()
        completedEvent.shouldBeInstanceOf<AdditionalTaskEvents.RepsCompletedEvent>()
        completedEvent.taskId shouldBe taskId
    }
    "task in planned state can complete reps"{
        val taskId = "taskId1"
        val task = taskInPlanned(taskId = taskId)
        val state = TaskStatus.Active
        val result = state.canCompleteReps()
        result.shouldBeRight()
    }


// Переход PLANNED → COMPLETED (автоматически)
    "reps completed reaches reps target, progress state should be done"{
        val task = taskHandstand(targetReps = 300)
        task.completeReps(Rep(300))
        task.progress() shouldBe TaskProgressStatus.DONE
    }
    "planned task becomes completed after first completed reps their reaches target reps"{
        val task = taskInPlanned(targetReps = 30)
        task.completeReps(Rep(30))
        val completedEvent = task.popEvents().last()
        completedEvent.shouldBeInstanceOf<AdditionalTaskEvents.TaskCompletedEvent>()
        task.status() shouldBe TaskStatus.Completed
    }


// Переход PLANNED → CANCELLED
    "task can cancelling in planned state"{
        val task = taskHandstand()
        task.cancel()
        task.status() shouldBe TaskStatus.Cancelled
    }

// Переход PLANNED → COMPLETED (вручную)
    "task in planned state and has not been completed reps can be completed manually"{
        val task = taskInPlanned()
        task.complete()
        task.status() shouldBe TaskStatus.Completed
        task.progress() shouldBe TaskProgressStatus.NOT_STARTED
    }





    // Создание ACTIVE (через completeReps из PLANNED)
    "complete reps in planned state task, should be set state task is completed and progress is done"{
        val task = taskActive(repsTarget = 30)  // уже ACTIVE
        task.completeReps(Rep(30))
        task.status() shouldBe TaskStatus.Completed
        task.progress() shouldBe TaskProgressStatus.DONE
    }

// Переход ACTIVE → COMPLETED (автоматически)


// Переход ACTIVE → COMPLETED (вручную)
    "task in active state and has been completed reps can be completed manually"{
        val task = taskActive(repsTarget = 300, repsCompleted = 10)
        task.complete()
        task.status() shouldBe TaskStatus.Completed
        task.progress() shouldBe TaskProgressStatus.IN_PROGRESS
        task.repsRemaining() shouldBe Rep(290)
    }

// Переход ACTIVE → CANCELLED
    "task can cancelling in active state"{
        val task = taskActive()
        task.cancel()  // из PLANNED, но cancel работает из любого состояния
        task.status() shouldBe TaskStatus.Cancelled
        val cancelledEvent = task.popEvents().last()
        cancelledEvent.shouldBeInstanceOf<AdditionalTaskEvents.TaskCancelledEvent>()
    }

// Прогноз следующего варианта количества повторений, проверка для ACTIVE
    "proposed execution quantity for next step"{
        val task = taskActive(repsTarget = 30)
        task.proposedQuantity() shouldBe 9
    }




    // Запрет операций на COMPLETED
    "can not cancelled completed task"{
        val task = taskCompleted()
        val result = task.cancel()
        val error = result.shouldBeLeft()
        error.shouldBeInstanceOf<AdditionalTaskError.TaskIsCompleted>()
    }
    "can not complete reps in completed task state"{
        val task = taskCompleted()
        val result = task.completeReps(Rep(10))
        val error = result.shouldBeLeft()
        error.shouldBeInstanceOf<AdditionalTaskError.TaskIsCompleted>()
    }
    "can not complete in completed task state"{
        val task = taskCompleted()
        val result = task.complete()
        val error = result.shouldBeLeft()
        error.shouldBeInstanceOf<AdditionalTaskError.TaskIsCompleted>()
    }


    // Запрет операций на CANCELLED
    "cancelled task cannot complete reps"{
        val task = taskHandstand()
        task.cancel()
        val result = task.completeReps(Rep(10))
        val error = result.shouldBeLeft()
        error.shouldBeInstanceOf<AdditionalTaskError.TaskIsCancelled>()
    }



    "transition from planned to active to completed"{
        val task = taskInPlanned(targetReps = 50)
        task.status() shouldBe TaskStatus.Planned

        task.completeReps(Rep(30))
        val events = task.popEvents()
        val repsCompletedEvent = events.first()
        repsCompletedEvent.shouldBeInstanceOf<AdditionalTaskEvents.RepsCompletedEvent>()

        task.completeReps(Rep(20))
        val taskCompletedEvent = task.popEvents().last()
        taskCompletedEvent.shouldBeInstanceOf<AdditionalTaskEvents.TaskCompletedEvent>()
    }

    "in progress task should return remaining reps"{
        val task = taskInPlanned(targetReps = 30)
        task.completeReps(Rep(10))  // теперь ACTIVE
        task.repsRemaining() shouldBe Rep(20)
    }
})

