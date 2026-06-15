package ru.workout.session.usecase

import arrow.core.Either
import arrow.core.raise.context.either
import arrow.core.raise.ensure
import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import ru.workout.session.domain.SessionStatus

class AdditionalSessionTest: StringSpec({
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
        ))
        result.shouldBeLeft()
    }

    "can beginning task in progress status"{
        val taskHandstand = taskHandstand(status = AdditionalTaskStatus.IN_PROGRESS)
        val result = taskHandstand.completeStep(
            AdditionalStep(
                stepId = "step1",
                actualTime = 14,
            ))
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
            )).shouldBeRight()
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
            ))
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

private fun taskWithFirstStepCompleted(actualTime: Int): AdditionalTask {
    val taskHandstand = taskHandstand(
        status = AdditionalTaskStatus.IN_PROGRESS,
        targetTime = actualTime * 2)
    taskHandstand.completeStep(
        AdditionalStep(
            stepId = "step1",
            actualTime = actualTime,
        ))
    return taskHandstand
}

open class AdditionalStep(
    val stepId: String,
    val actualTime: Int,
//    val status: Any,
//    val task: AdditionalTask
) {

}

open class AdditionalSession(tasks: Any, status: SessionStatus) {
    fun completeStep(taskId: Any) {}

}

fun taskHandstand(
    targetTime: Int = 300,
    exerciseName: String = "стойка на руках",
    taskId: String = "task-1",
    steps: MutableList<AdditionalStep> = mutableListOf<AdditionalStep>(),
    status: AdditionalTaskStatus = AdditionalTaskStatus.PLANNED
) = AdditionalTask(
    taskId = taskId,
    exerciseName = exerciseName,
    targetTime = targetTime,
    steps = steps,
    status = status
)

class AdditionalTask(
    val taskId: Any,
    val exerciseName: Any,
    val targetTime: Int,
    val steps: MutableList<AdditionalStep>,
    var status: AdditionalTaskStatus
) {
    fun remainsCompleted(): Int {
        return targetTime - steps.sumOf { it.actualTime }
    }

    fun status(): AdditionalTaskStatus {
        return status
    }

    fun completeStep(step: AdditionalStep)
    : Either<AdditionalTaskError, Unit> = either{
        ensure(status == AdditionalTaskStatus.IN_PROGRESS){
            AdditionalTaskError.TaskNotInProgress
        }
        steps.add(step)
        if (targetTimeWillBeCompletedWithStep(step)){
            status = AdditionalTaskStatus.COMPLETED
        } else {
            status = AdditionalTaskStatus.IN_PROGRESS
        }

    }
    fun targetTimeWillBeCompletedWithStep(step: AdditionalStep): Boolean{
        return steps.sumOf { it.actualTime } >= targetTime
    }

    fun proposedQuantity(): Int {
        return kotlin.math.floor(lastActualTime() * percent(5.0)).toInt()
    }

    private fun lastActualTime(): Int = steps.last().actualTime
    private fun percent(percent: Double): Double =
        (1 - percent / 100)

}

sealed interface AdditionalTaskError {
    object TaskNotInProgress: AdditionalTaskError
}

enum class AdditionalTaskStatus {
    PLANNED,
    IN_PROGRESS,
    COMPLETED
}

class TaskTemplate(
    val stepTemplateId: String,
    val exerciseName: String,
    val targetTime: Int
)
