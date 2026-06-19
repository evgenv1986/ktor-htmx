package ru.workout.session.domain.additional

import arrow.core.Either
import arrow.core.raise.context.either
import arrow.core.raise.ensure
import ru.workout.common.event.DomainEvent
import ru.workout.session.domain.DomainEntity
import kotlin.math.floor

open class AdditionalStep(
    val stepId: String,
    val actualReps: Int,
)

class AdditionalTask(
    val taskId: String,
    val exerciseName: String,
    val targetReps: Rep,
    val steps: MutableList<AdditionalStep>,
    val completedRepsList: MutableList<Rep>,
//    val completedReps: Reps
): DomainEntity() {
    fun remainsCompleted(): Int {
        TODO()
//        return targetReps - steps.sumOf { it.actualReps }
    }

    fun status(): AdditionalTaskStatus {
        return when{
            targetReps.isReachedBy(completedRepsList) -> AdditionalTaskStatus.COMPLETED
            completedRepsIsNotEmpty()  -> AdditionalTaskStatus.IN_PROGRESS
            else -> AdditionalTaskStatus.PLANNED
        }
    }
    private fun completedRepsIsNotEmpty(): Boolean
        = completedRepsList.sumOf { it.value } > 0

    fun completeStep(step: AdditionalStep)
    : Either<AdditionalTaskError, Unit> = either {
        ensure(status() == AdditionalTaskStatus.IN_PROGRESS) {
            AdditionalTaskError.TaskNotInProgress
        }
        steps.add(step)
    }
    fun proposedQuantity(): Int {
        return floor(lastCompletedRepsCount() * percent(5.0)).toInt()
    }
    private fun lastCompletedRepsCount(): Int = steps.last().actualReps
    private fun percent(percent: Double): Double =
        (1 - percent / 100)

    fun completeReps(reps: Rep) {
        completedRepsList.add(reps)
            .apply{addEvent(AdditionalTaskEvents
                .RepsCompletedEvent(taskId) )
            }
    }
    fun repsRemaining() {
//        Difference(targetReps, completedRepsList)
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