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
    private var status: TaskStatus = TaskStatus.Planned

    fun status(): TaskStatus {
        return status
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
        ensure(status != TaskStatus.Cancelled){
            AdditionalTaskError.TaskIsCancelled
        }
        ensure (status != TaskStatus.Completed)
        {
            AdditionalTaskError.TaskIsCompleted
        }

        repsCompleted.add(rep)
            .apply{ addEvent(
                AdditionalTaskEvents
                    .RepsCompletedEvent(taskId) )
            }
            status.setupNextState(this@AdditionalTask, rep)
    }
    public fun complete() {
        changeStatus(
            TaskStatus.Completed,
            AdditionalTaskEvents.TaskCompletedEvent(taskId)
        )
    }
    fun activate(){
        changeStatus(
            TaskStatus.Active,
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
        ensure(status != TaskStatus.Cancelled){
            AdditionalTaskError.TaskAlreadyCancelled
        }
        ensure(status != TaskStatus.Completed){
            AdditionalTaskError.TaskIsCompleted
        }
        changeStatus(
            TaskStatus.Cancelled,
            AdditionalTaskEvents.TaskCancelledEvent(taskId)
        )
    }
    internal fun changeStatus(
       newStatus: TaskStatus,
       event: DomainEvent
    ) {
       if (newStatus == status) return
       this.status = newStatus
       addEvent(event)
    }
    fun targetReached(): Boolean =
        repsCompleted.isReachedBy(repsTarget)
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
sealed interface TaskStatus{

    fun setupNextState(task: AdditionalTask, rep: Rep)
    object Planned: TaskStatus{
        override fun setupNextState(task: AdditionalTask, rep: Rep){
            if (task.targetReached()){
                task.complete()
            } else {
                task.activate()
            }
        }
    }
    object Active: TaskStatus {
        override fun setupNextState(
            task: AdditionalTask,
            rep: Rep
        ) {
            if (task.targetReached()) {
                task.complete()
            }
        }

    }
    object Cancelled: TaskStatus {
        override fun setupNextState(
            task: AdditionalTask,
            rep: Rep
        ) {
            return
        }
    }
    object Completed: TaskStatus{
        override fun setupNextState(
            task: AdditionalTask,
            rep: Rep
        ) {}
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