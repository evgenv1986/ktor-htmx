package com.example.rest.executionStep.task

class TaskOfStepById(val id: Int?) {
    fun invoke(): TaskOfStep {
        return TaskOfStep(
            "Подтягивания",
            12.1,
            20
        )
    }

}