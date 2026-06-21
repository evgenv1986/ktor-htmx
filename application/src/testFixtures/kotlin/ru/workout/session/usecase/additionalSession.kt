package ru.workout.session.usecase

import ru.workout.session.domain.SessionStatus
import ru.workout.session.domain.additional.AdditionalSession
import ru.workout.session.domain.additional.AdditionalStep
import ru.workout.session.domain.additional.AdditionalTask
import ru.workout.session.domain.additional.Rep
import ru.workout.session.domain.additional.Reps

fun taskHandstand (
    exerciseName: String = "handstand",
    targetReps: Int = 300,
    taskId: String = "task1"
): AdditionalTask = AdditionalTask(
    taskId = taskId,
    exerciseName = exerciseName,
    repsTarget = Rep(targetReps),
    repsCompleted = Reps(mutableListOf<Rep>())
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
    repsTarget = Rep(targetReps),
    repsCompleted = Reps(mutableListOf<Rep>())
)
fun taskBeginned(
    exerciseName: String = "handstand",
    targetReps: Int = 300,
    taskId: String = "task1"
): AdditionalTask {
    val task = AdditionalTask(
        taskId = taskId,
        exerciseName = exerciseName,
        repsTarget = Rep(targetReps),
        repsCompleted = Reps(mutableListOf<Rep>())
    )
    task.begin()
    return task
}
fun taskInProgressWithRepsCompleted(
    exerciseName: String = "handstand",
    repsTarget: Int = 300,
    taskId: String = "task1",
    repsCompleted: Int = 10
): AdditionalTask {
    val task = AdditionalTask(
        taskId = taskId,
        exerciseName = exerciseName,
        repsTarget = Rep(repsTarget),
        repsCompleted = Reps(mutableListOf<Rep>(
            Rep(repsCompleted)
        ))
    )
    task.begin()
    return task
}
