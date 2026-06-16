package ru.workout.session.usecase

import ru.workout.session.usecase.additional.AdditionalStep
import ru.workout.session.usecase.additional.AdditionalTask
import ru.workout.session.usecase.additional.AdditionalTaskStatus


fun taskWithFirstStepCompleted(
    actualTime: Int = 14)
        : AdditionalTask {
    val taskHandstand = taskHandstand(
        status = AdditionalTaskStatus.IN_PROGRESS,
        targetTime = actualTime * 2)
    taskHandstand.completeStep(
        AdditionalStep(
            stepId = "step1",
            actualTime = actualTime,
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
    targetTime = targetTime,
    steps = steps,
    status = status
)