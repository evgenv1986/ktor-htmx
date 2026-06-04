package ru.workout.session.domain

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure

class SessionRoutine(
    val planId: Int,
    val exercises: List<String>) {
    companion object {
        fun from(
            planId: Int,
            exercises: List<String>
        ): Either<SessionRoutineError, SessionRoutine> = either{
            ensure(!exercises.isEmpty()){
              SessionRoutineError.EmptyExercises
            }
            SessionRoutine(planId, exercises)
        }
    }

}

interface SessionRoutineError {
    object EmptyExercises: SessionRoutineError
}
