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

fun step(
    stepId: String = "s1",
    actualReps: Int = 20,
    status: StepStatus = StepStatus.PLANNED
): SessionStep {
    val step = SessionStep(
        stepId = stepId,
        actualReps = actualReps,
        status = status
    )
    return step
}