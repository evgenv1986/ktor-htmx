package ru.workout.session.domain.additional

import arrow.core.Either
import arrow.core.left
import arrow.core.raise.either

sealed interface TaskStatus{

    fun setupNextState(task: AdditionalTask, rep: Rep)
    fun canCompleteReps(task: AdditionalTask, rep: Rep): Either<AdditionalTaskError, Unit>
    fun nextState(): TaskStatus
    object Planned: TaskStatus{
        override fun setupNextState(task: AdditionalTask, rep: Rep){
            if (task.targetReached()){
                task.complete()
            } else {
                task.activate()
            }
        }
        override fun canCompleteReps(
            task: AdditionalTask,
            rep: Rep
        ): Either<AdditionalTaskError, Unit> {
            return either { Unit }
        }
        override fun nextState(): TaskStatus {
            TODO("Not yet implemented")
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

        override fun nextState(): TaskStatus {
            TODO("Not yet implemented")
        }
    }
    object Cancelled: TaskStatus {
        override fun setupNextState(
            task: AdditionalTask,
            rep: Rep
        ) {
            return
        }

        override fun canCompleteReps(
            task: AdditionalTask,
            rep: Rep
        ): Either<AdditionalTaskError, Unit> =
            AdditionalTaskError.TaskIsCancelled.left()

        override fun nextState(): TaskStatus {
            TODO("Not yet implemented")
        }
    }
    object Completed: TaskStatus{
        override fun setupNextState(
            task: AdditionalTask,
            rep: Rep
        ) {}

        override fun canCompleteReps(
            task: AdditionalTask,
            rep: Rep
        ): Either<AdditionalTaskError, Unit> =
            AdditionalTaskError.TaskIsCompleted.left()


        override fun nextState(): TaskStatus {
            TODO("Not yet implemented")
        }
    }
}