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
    private var status: AdditionalTaskStatus = AdditionalTaskStatus.PLANNED

    fun status(): AdditionalTaskStatus {
        return status
//        return when{
//            repsTarget.isReachedBy(repsCompleted) -> AdditionalTaskStatus.COMPLETED
//            repsCompleted.isNotEmpty()  -> AdditionalTaskStatus.IN_PROGRESS
//            else -> AdditionalTaskStatus.PLANNED
//        }
    }
    fun progress(): TaskProgressStatus{
        return when {
            repsTarget.isReachedBy(repsCompleted) ->
                TaskProgressStatus.DONE
            repsCompleted.isNotEmpty() -> TaskProgressStatus.IN_PROGRESS
            else -> TaskProgressStatus.NOT_STARTED
        }
    }
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
        tryCompleteTask()
    }
    fun tryCompleteTask(){
        if (repsTarget.isReachedBy(repsCompleted)){
            complete()
        }
    }
    public fun complete() {
        changeStatus(
            AdditionalTaskStatus.COMPLETED,
            AdditionalTaskEvents.TaskCompletedEvent(taskId)
        )
    }

    fun proposedQuantity(): Int {
        return floor(lastCompletedRepsCount() * percent(5.0)).toInt()
    }
    private fun lastCompletedRepsCount(): Int =
        repsCompleted.lastActualRep().intValue()
    private fun percent(percent: Double): Double =
        (1 - percent / 100)

    fun repsRemaining(): Rep {
        return Difference(repsTarget, repsCompleted).calc()
    }
    fun repsCompleted(): Int{
        return repsCompleted.totalReps()
    }

    fun cancel() {
        changeStatus(
            AdditionalTaskStatus.CANCELLED,
            AdditionalTaskEvents.TaskCancelledEvent(taskId)
        )
    }

   private fun changeStatus(
        newStatus: AdditionalTaskStatus,
        event: DomainEvent
   ) {
        this.status = newStatus
        addEvent(event)
   }

   fun begin() {
        changeStatus(
            AdditionalTaskStatus.IN_PROGRESS,
            AdditionalTaskEvents.TaskBeginningEvent(taskId)
        )
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
    object CompleteRepsOfCancelledTask: AdditionalTaskError
    object CompleteRepsOfTaskCompleted: AdditionalTaskError
}
enum class AdditionalTaskStatus {
    PLANNED,
    IN_PROGRESS,
    COMPLETED,
    CANCELLED
}
sealed class AdditionalTaskEvents(val taskId: String
): DomainEvent {
    class TaskCompletedEvent(taskId: String) : AdditionalTaskEvents(taskId)
    class TaskBeginningEvent(taskId: String): AdditionalTaskEvents(taskId)
    class RepsCompletedEvent(taskId: String): AdditionalTaskEvents(taskId)
    class TaskCancelledEvent(taskId: String): AdditionalTaskEvents(taskId)
}

enum class TaskProgressStatus {
    NOT_STARTED,
    DONE,
    IN_PROGRESS,

}