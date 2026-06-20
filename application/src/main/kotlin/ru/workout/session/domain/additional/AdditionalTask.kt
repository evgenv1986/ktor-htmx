package ru.workout.session.domain.additional

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
    val completedReps: Reps
): DomainEntity() {
       fun status(): AdditionalTaskStatus {
        return when{
            targetReps.isReachedBy(completedReps) -> AdditionalTaskStatus.COMPLETED
            completedReps.isNotEmpty()  -> AdditionalTaskStatus.IN_PROGRESS
            else -> AdditionalTaskStatus.PLANNED
        }
    }
    fun proposedQuantity(): Int {
        return floor(lastCompletedRepsCount() * percent(5.0)).toInt()
    }
    private fun lastCompletedRepsCount(): Int =
        completedReps.lastActualRep().intValue()
    private fun percent(percent: Double): Double =
        (1 - percent / 100)
    fun completeReps(rep: Rep) {
        completedReps.add(rep)
            .apply{ addEvent(
                AdditionalTaskEvents
                .RepsCompletedEvent(taskId) )
            }
    }
    fun repsRemaining(): Rep {
        return Difference(targetReps, completedReps).calc()
    }
}

class Difference(
    val targetReps: Rep,
    val completedReps: Reps
) {
    fun calc(): Rep {
        return targetReps.minus(completedReps)
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
