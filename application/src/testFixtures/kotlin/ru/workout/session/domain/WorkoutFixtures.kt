package ru.workout.session.domain

fun session(
    routine: SessionRoutine = SessionRoutine(123, listOf("Приседания","Подтягивания")),
    sessionId: Int = 1,
    status: SessionStatus = SessionStatus.PREPARED
): WorkoutSession{
    return WorkoutSession(
        routine,
        planId = routine.planId,
        exercises = routine.exercises,
        sessionId,
        status,
    )
}