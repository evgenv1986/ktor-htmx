package ru.workout.session.domain.additional

import arrow.core.Either
import arrow.core.left
import arrow.core.raise.either

sealed interface TaskStatus{

    fun canCompleteReps(task: AdditionalTask, rep: Rep): Either<AdditionalTaskError, Unit>
    fun canCancel(): Either<AdditionalTaskError, Unit>
    fun nextState(): TaskStatus
    object Planned: TaskStatus{
        override fun canCompleteReps(
            task: AdditionalTask,
            rep: Rep
        ): Either<AdditionalTaskError, Unit> {
            return either { Unit }
        }

        override fun canCancel(): Either<AdditionalTaskError, Unit> = either {}

        override fun nextState(): TaskStatus {
            TODO("Not yet implemented")
        }
    }
    object Active: TaskStatus {
        override fun canCompleteReps(
            task: AdditionalTask,
            rep: Rep
        ): Either<AdditionalTaskError, Unit> {
            return either { Unit }
//            ensure(task.status() != TaskStatus.Completed) {
//                AdditionalTaskError.TaskIsCompleted
//            }
//            ensure(task.status() != TaskStatus.Cancelled) {
//                AdditionalTaskError.TaskIsCancelled
//            }
        }

        override fun canCancel(): Either<AdditionalTaskError, Unit> = either {}

        override fun nextState(): TaskStatus {
            TODO("Not yet implemented")
        }
    }
    object Cancelled: TaskStatus {
        override fun canCompleteReps(
            task: AdditionalTask,
            rep: Rep
        ): Either<AdditionalTaskError, Unit> =
            AdditionalTaskError.TaskIsCancelled.left()

        override fun canCancel(): Either<AdditionalTaskError, Unit> =
            AdditionalTaskError.TaskIsCancelled.left()

        override fun nextState(): TaskStatus {
            TODO("Not yet implemented")
        }
    }
    object Completed: TaskStatus{
        override fun canCompleteReps(
            task: AdditionalTask,
            rep: Rep
        ): Either<AdditionalTaskError, Unit> =
            AdditionalTaskError.TaskIsCompleted.left()

        override fun canCancel(): Either<AdditionalTaskError, Unit> =
            AdditionalTaskError.TaskIsCancelled.left()


        override fun nextState(): TaskStatus {
            TODO("Not yet implemented")
        }
    }
}