package ru.workout.session.usecase

import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import ru.workout.session.domain.additional.AdditionalTaskError
import ru.workout.session.domain.additional.AdditionalTaskEvents
import ru.workout.session.domain.additional.Rep
import ru.workout.session.domain.additional.Reps
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
    "the status of a partially completed task should be in progress"{
        val task = taskInPlanned()
        task.completeReps(Rep(10))
        task.status() shouldBe TaskStatus.Active
    }
    "can complete reps for task in progress state"{
        val task = taskInPlanned("handstand", 300, "task1")
        task.completeReps(Rep(14))
        // проверяет событие и taskId
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
    "task with fully completed target reps must have status completed"{
        val task = taskInPlanned(targetReps = 30)
        task.completeReps(Rep(30))
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
    "task completed reps is reached by target reps then task completion and progress is done"{
        val task = taskActive(repsTarget = 30)  // уже ACTIVE
        task.completeReps(Rep(30))
        task.status() shouldBe TaskStatus.Completed
        task.progress() shouldBe TaskProgressStatus.DONE
    }

// Переход ACTIVE → COMPLETED (автоматически)
    "in progress task should return remaining reps"{
        val task = taskInPlanned(targetReps = 30)
        task.completeReps(Rep(10))  // теперь ACTIVE
        task.repsRemaining() shouldBe Rep(20)
    }

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
        val task = taskHandstand()
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
    "can not complete rep for task in completed status"{
        val task = taskHandstand(targetReps = 30)
        task.completeReps(Rep(30))  // стала COMPLETED
        task.completeReps(Rep(10)).shouldBeLeft()
            .shouldBeInstanceOf<AdditionalTaskError.TaskIsCompleted>()
        task.repsCompleted() shouldBe 30
    }




    // Запрет операций на CANCELLED
    "cancelled task cannot complete reps"{
        val task = taskHandstand()
        task.cancel()
        val result = task.completeReps(Rep(10))
        val error = result.shouldBeLeft()
        error.shouldBeInstanceOf<AdditionalTaskError.TaskIsCancelled>()
    }

    "the amount of completed repetitions reached the target reps"{
        val repsCompleted = Reps(mutableListOf(Rep(2)))
        val targetReps = Rep(5)
        repsCompleted.add(Rep(3))
        repsCompleted.isReachedBy(targetReps).shouldBeTrue()
    }

    "transition from planned to active to completed"{
        val task = taskInPlanned(targetReps = 50)
        task.status() shouldBe TaskStatus.Planned

        task.completeReps(Rep(30))
        val events = task.popEvents()
        val repsCompletedEvent = events.first()
        repsCompletedEvent.shouldBeInstanceOf<AdditionalTaskEvents.RepsCompletedEvent>()
        val taskBeginningEvent = events.last()

        task.completeReps(Rep(20))
        val taskCompletedEvent = task.popEvents().last()
        taskCompletedEvent.shouldBeInstanceOf<AdditionalTaskEvents.TaskCompletedEvent>()
    }
})

