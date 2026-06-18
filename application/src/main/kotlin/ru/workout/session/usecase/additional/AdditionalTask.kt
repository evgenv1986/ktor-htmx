package ru.workout.session.usecase.additional

import arrow.core.Either
import arrow.core.raise.context.either
import arrow.core.raise.ensure
import kotlin.math.floor

open class AdditionalStep(
    val stepId: String,
    val actualTime: Int,
//    val status: Any,
//    val task: AdditionalTask
) {

}

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
    : Either<AdditionalTaskError, Unit> = either {
        ensure(status == AdditionalTaskStatus.IN_PROGRESS) {
            AdditionalTaskError.TaskNotInProgress
        }
        steps.add(step)
        if (targetTimeWillBeCompletedWithStep(step)) {
            status = AdditionalTaskStatus.COMPLETED
        } else {
            status = AdditionalTaskStatus.IN_PROGRESS
        }
    }
    fun targetTimeWillBeCompletedWithStep(step: AdditionalStep): Boolean{
        return steps.sumOf { it.actualTime } >= targetTime
    }

    fun proposedQuantity(): Int {
        return floor(lastActualTime() * percent(5.0)).toInt()
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