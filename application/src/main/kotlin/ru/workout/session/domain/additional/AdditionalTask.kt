package ru.workout.session.domain.additional

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import ru.workout.common.event.DomainEvent
import ru.workout.session.domain.DomainEntity
import kotlin.math.floor

open class AdditionalStep(
    val stepId: String,
    val actualReps: Int,
)

class AdditionalTask(
    private val taskId: String,
    private val exerciseName: String,
    private val repsTarget: Rep,
    private val repsCompleted: Reps
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
    fun completeReps(rep: Rep)
    : Either<AdditionalTaskError, Unit> = either {
        ensure(status() != AdditionalTaskStatus.COMPLETED){
            AdditionalTaskError.CompleteRepsOfTaskCompleted
        }
        repsCompleted.add(rep)
            .apply{ addEvent(
                AdditionalTaskEvents
                .RepsCompletedEvent(taskId) )
            }
    }
    fun repsRemaining(): Rep {
        return Difference(repsTarget, repsCompleted).calc()
    }
    fun repsCompleted(): Int{
        return repsCompleted.totalReps()
    }

    fun cancel() {
        TODO("Not yet implemented")
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
    object CompleteRepsOfTaskCompleted: AdditionalTaskError
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
