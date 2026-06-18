package ru.workout.session.usecase

import ru.workout.session.domain.SessionStatus
import ru.workout.session.usecase.additional.AdditionalSession
import ru.workout.session.usecase.additional.AdditionalStep
import ru.workout.session.usecase.additional.AdditionalTask
import ru.workout.session.usecase.additional.AdditionalTaskStatus
import ru.workout.session.usecase.additional.Reps


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
    targetReps = Reps(targetTime),
    steps = steps,
    completedReps = mutableListOf()
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
    targetReps = Reps(targetReps),
    steps = mutableListOf<AdditionalStep>(),
    completedReps = mutableListOf<Reps>()
)