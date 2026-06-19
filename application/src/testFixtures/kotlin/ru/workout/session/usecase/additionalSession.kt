package ru.workout.session.usecase

import ru.workout.session.domain.SessionStatus
import ru.workout.session.domain.additional.AdditionalSession
import ru.workout.session.domain.additional.AdditionalStep
import ru.workout.session.domain.additional.AdditionalTask
import ru.workout.session.domain.additional.AdditionalTaskStatus
import ru.workout.session.domain.additional.Rep


fun taskWithFirstStepCompleted(
    actualTime: Int = 14)
        : AdditionalTask {
    val taskHandstand = taskHandstand(
        status = AdditionalTaskStatus.IN_PROGRESS,
        targetTime = actualTime * 2)
    taskHandstand.completeStep(
        AdditionalStep(
            stepId = "step1",
            actualReps = actualTime,
        )
    )
    return taskHandstand
}

fun taskHandstand(
    targetTime: Int = 300,
    exerciseName: String = "стойка на руках",
    taskId: String = "task-1",
    steps: MutableList<AdditionalStep> = mutableListOf<AdditionalStep>(),
    status: AdditionalTaskStatus = AdditionalTaskStatus.PLANNED
) = AdditionalTask(
    taskId = taskId,
    exerciseName = exerciseName,
    targetReps = Rep(targetTime),
    steps = steps,
    completedRepsList = mutableListOf()
)

fun step(
    stepId: String = "step1",
    actualTime: Int = 14
) = AdditionalStep(
        stepId = stepId,
        actualReps = actualTime,
    )

fun additionalSession(
    tasks: List<AdditionalTask> = listOf(taskHandstand()),
    status: SessionStatus = SessionStatus.IN_PROGRESS
) = AdditionalSession(
    tasks = tasks,
    status = status
)
fun taskInProgress (
    exerciseName: String = "handstand",
    targetReps: Int = 300,
    taskId: String = "task1"
): AdditionalTask = AdditionalTask(
    taskId = taskId,
    exerciseName = exerciseName,
    targetReps = Rep(targetReps),
    steps = mutableListOf<AdditionalStep>(),
    completedRepsList = mutableListOf<Rep>()
)