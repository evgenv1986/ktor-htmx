package ru.workout.session.usecase

import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import ru.workout.session.domain.additional.AdditionalTask
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
})

