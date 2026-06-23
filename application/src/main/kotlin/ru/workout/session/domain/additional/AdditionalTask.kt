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
    internal val taskId: String,
    internal val exerciseName: String,
    internal val repsTarget: Rep,
    internal val repsCompleted: Reps
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
        ensure(status != AdditionalTaskStatus.CANCELLED){
            AdditionalTaskError.TaskIsCancelled
        }
        ensure (status != AdditionalTaskStatus.COMPLETED)
        {
            AdditionalTaskError.TaskIsCompleted
        }
        repsCompleted.add(rep)
            .apply{ addEvent(
                AdditionalTaskEvents
                    .RepsCompletedEvent(taskId) )
            }
        TaskStatus2.PlannedStatus()
            .tryToSetNextStep(this@AdditionalTask, rep)
//        tryCompleteTask()
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
    fun activate(){
        changeStatus(
            AdditionalTaskStatus.ACTIVE,
            AdditionalTaskEvents.TaskBeginningEvent(taskId)
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

    fun cancel()
    :Either<AdditionalTaskError, Unit> = either {
        ensure(status != AdditionalTaskStatus.CANCELLED){
            AdditionalTaskError.TaskAlreadyCancelled
        }
        ensure(status != AdditionalTaskStatus.COMPLETED){
            AdditionalTaskError.TaskIsCompleted
        }
        changeStatus(
            AdditionalTaskStatus.CANCELLED,
            AdditionalTaskEvents.TaskCancelledEvent(taskId)
        )
    }

   internal fun changeStatus(
        newStatus: AdditionalTaskStatus,
        event: DomainEvent
   ) {
        this.status = newStatus
        addEvent(event)
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
    object TaskIsCancelled: AdditionalTaskError
    object TaskIsCompleted: AdditionalTaskError
    object TaskAlreadyCancelled: AdditionalTaskError
}
enum class AdditionalTaskStatus(
    private val nextStates: Set<AdditionalTaskStatus> = emptySet()
) {
    COMPLETED(),
    CANCELLED(),
    ACTIVE(nextStates = setOf(COMPLETED, CANCELLED)),
    PLANNED(nextStates = setOf(ACTIVE, COMPLETED, CANCELLED));
    private fun canChangeTo (state: AdditionalTaskStatus) = nextStates.contains(state)
}
sealed interface TaskStatus2{
    fun tryToSetNextStep(task: AdditionalTask, rep: Rep)
    class PlannedStatus(): TaskStatus2{
        override fun tryToSetNextStep(task: AdditionalTask, rep: Rep) {
            if (task.repsTarget.isReachedBy(Reps(mutableListOf(rep)))) {
                task.changeStatus(
                    AdditionalTaskStatus.COMPLETED,
                    ru.workout.session.domain.additional.AdditionalTaskEvents.TaskCompletedEvent(task.taskId)
                )
            } else {
                task.activate()
            }
        }
    }
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
    IN_PROGRESS,
    DONE,
}