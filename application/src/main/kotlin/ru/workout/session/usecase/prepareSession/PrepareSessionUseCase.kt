package ru.workout.session.usecase.prepareSession

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import arrow.core.right
import ru.workout.session.domain.SessionIdStore
import ru.workout.session.domain.WorkoutSession
import ru.workout.session.usecase.ExtractWorkoutSession
import ru.workout.session.usecase.SaveWorkoutSession
import ru.workout.session.usecase.WorkoutPlanProvider

class PrepareSessionUseCase(
    val provider: WorkoutPlanProvider,
    val extractSession: ExtractWorkoutSession,
    val saveSessionStore: SaveWorkoutSession,
    val idStore: SessionIdStore
) {
    operator fun invoke(request: PrepareSessionRequest
    ): Either<PrepareSessionUseCaseError, Int> = either {
        return TODO()
//        val plan = provider.workoutPlanById(request.catalogWorkoutId)
//        ensure (plan.shouldBeRight()){
//            PrepareSessionUseCaseError.
//        }
//        val routine = WorkoutSession.from(plan)
//        val sessionId = idStore.nextId()
//        val session = WorkoutSession.prepare(routine, idStore)
//        session.sessionId.right()
    }
}

interface PrepareSessionUseCaseError {

}

