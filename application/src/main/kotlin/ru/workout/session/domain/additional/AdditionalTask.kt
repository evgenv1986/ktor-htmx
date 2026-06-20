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
    val repsTarget: Rep,
    val repsCompleted: Reps
): DomainEntity() {
       fun status(): AdditionalTaskStatus {
        return when{
            repsTarget.isReachedBy(repsCompleted) -> AdditionalTaskStatus.COMPLETED
            repsCompleted.isNotEmpty()  -> AdditionalTaskStatus.IN_PROGRESS
            else -> AdditionalTaskStatus.PLANNED
        }
    }
    fun proposedQuantity(): Int {
        return floor(lastCompletedRepsCount() * percent(5.0)).toInt()
    }
    private fun lastCompletedRepsCount(): Int =
        repsCompleted.lastActualRep().intValue()
    private fun percent(percent: Double): Double =
        (1 - percent / 100)
    fun completeReps(rep: Rep) {
        repsCompleted.add(rep)
            .apply{ addEvent(
                AdditionalTaskEvents
                .RepsCompletedEvent(taskId) )
            }
    }
    fun repsRemaining(): Rep {
        return Difference(repsTarget, repsCompleted).calc()
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
    object NotInProgress: AdditionalTaskError
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
