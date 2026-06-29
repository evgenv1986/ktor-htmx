package ru.workout.session.domain.additional

import arrow.core.Either
import arrow.core.left
import arrow.core.raise.either

sealed interface TaskStatus{
    fun canCompleteReps(): Either<AdditionalTaskError, Unit>
    fun canCancel(): Either<AdditionalTaskError, Unit>
    fun canActivate(): Either<AdditionalTaskError, Unit>
    object Planned: TaskStatus{
        override fun canCompleteReps(): Either<AdditionalTaskError, Unit> = either {}
        override fun canCancel(): Either<AdditionalTaskError, Unit> = either {}
        override fun canActivate(): Either<AdditionalTaskError, Unit> = either {}
    }
    object Active: TaskStatus {
        override fun canCompleteReps(): Either<AdditionalTaskError, Unit> = either {}
        override fun canCancel(): Either<AdditionalTaskError, Unit> = either {}
        override fun canActivate(): Either<AdditionalTaskError, Unit> = either {
            AdditionalTaskError.TaskAlreadyActive
        }
    }
    object Cancelled: TaskStatus {
        override fun canCompleteReps(): Either<AdditionalTaskError, Unit> = either {
            raise(AdditionalTaskError.TaskIsCancelled)
        }
        override fun canCancel(): Either<AdditionalTaskError, Unit> = either {
            raise(AdditionalTaskError.TaskIsCancelled)
        }
        override fun canActivate(): Either<AdditionalTaskError, Unit> = either {
            raise(AdditionalTaskError.TaskIsCancelled)
        }
    }
    object Completed: TaskStatus{
        override fun canCompleteReps(): Either<AdditionalTaskError, Unit> = either {
            raise(AdditionalTaskError.TaskIsCompleted)
        }
        override fun canCancel(): Either<AdditionalTaskError, Unit> = either {
            raise(AdditionalTaskError.TaskIsCompleted)
        }
        override fun canActivate(): Either<AdditionalTaskError, Unit> = either {
            raise(AdditionalTaskError.TaskIsCompleted)
        }
    }
}