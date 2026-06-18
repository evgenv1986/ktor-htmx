package ru.workout.session.usecase.additional

import arrow.core.Either
import arrow.core.raise.context.either
import arrow.core.raise.ensure
import ru.workout.common.event.DomainEvent
import ru.workout.common.types.base.ValueObject
import ru.workout.session.domain.DomainEntity
import kotlin.math.floor

open class AdditionalStep(
    val stepId: String,
    val actualReps: Int,
)
open class Reps(
    val value: Int
): ValueObject {
    fun isReachedBy(other: List<Reps>): Boolean {
        return value <= other.sumOf { it.value }
    }
}

class AdditionalTask(
    val taskId: String,
    val exerciseName: String,
    val targetReps: Reps,
    val steps: MutableList<AdditionalStep>,
    val completedReps: MutableList<Reps>
): DomainEntity() {
    fun remainsCompleted(): Int {
        TODO()
//        return targetReps - steps.sumOf { it.actualReps }
    }

    fun status(): AdditionalTaskStatus {
        return when{
            targetReps.isReachedBy(completedReps) -> AdditionalTaskStatus.COMPLETED
            completedRepsIsNotEmpty()  -> AdditionalTaskStatus.IN_PROGRESS
            else -> AdditionalTaskStatus.PLANNED
        }

//        val status = targetReps.moreThan(completedReps)
//            : AdditionalTaskStatus.IN_PROGRESS
//            ? AdditionalTaskStatus.COMPLETED
//        return status
//        if (targetTimeWillBeCompletedWithStep(step)) {
//            return AdditionalTaskStatus.COMPLETED
//        } else {
//            return AdditionalTaskStatus.IN_PROGRESS
//        }
    }

    private fun completedRepsIsNotEmpty(): Boolean
        = completedReps.sumOf { it.value } > 0

    fun completeStep(step: AdditionalStep)
    : Either<AdditionalTaskError, Unit> = either {
        ensure(status() == AdditionalTaskStatus.IN_PROGRESS) {
            AdditionalTaskError.TaskNotInProgress
        }
        steps.add(step)

    }
    fun targetTimeWillBeCompletedWithStep(step: AdditionalStep): Boolean{
        TODO()
    //        return steps.sumOf { it.actualReps } >= targetReps
    }

    fun proposedQuantity(): Int {
        return floor(lastActualTime() * percent(5.0)).toInt()
    }

    private fun lastActualTime(): Int = steps.last().actualReps
    private fun percent(percent: Double): Double =
        (1 - percent / 100)

    fun completeReps(reps: Reps) {
        completedReps.add(reps)
            .apply{addEvent(AdditionalTaskEvents
                .RepsCompletedEvent(taskId) )
            }
    }

}

sealed interface AdditionalTaskError {
    object TaskNotInProgress: AdditionalTaskError
}

enum class AdditionalTaskStatus {
    PLANNED,
    IN_PROGRESS,
    COMPLETED
}
sealed class AdditionalTaskEvents(val taskId: String
): DomainEvent {
    class RepsCompletedEvent(taskId: String
    ) : AdditionalTaskEvents(taskId)
}

class TaskTemplate(
    val stepTemplateId: String,
    val exerciseName: String,
    val targetTime: Int
)