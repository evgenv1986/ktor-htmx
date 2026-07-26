package ru.workout.session.rest.task

import ru.workout.session.usecase.task.TaskSetView

class PresentTaskResponse {
    fun toResponse(taskSetView: TaskSetView): TaskResponse = TaskResponse(
        taskSetView.workoutId,
        taskSetView.setId,
        taskSetView.stepId,
        taskSetView.reps,
        taskSetView.exerciseName
    )
}