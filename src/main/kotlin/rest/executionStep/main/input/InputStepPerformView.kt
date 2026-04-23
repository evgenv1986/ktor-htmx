package com.example.rest.executionStep.main.input

import com.example.rest.executionStep.main.task.TaskOfStep
import kotlinx.html.ButtonType
import kotlinx.html.FlowContent
import kotlinx.html.InputType
import kotlinx.html.br
import kotlinx.html.button
import kotlinx.html.div
import kotlinx.html.form
import kotlinx.html.h3
import kotlinx.html.id
import kotlinx.html.input
import kotlinx.html.label

class InputStepPerformView() {
    fun FlowContent.invoke(task: TaskOfStep) {
        content(task)
    }
    fun FlowContent.content(task: TaskOfStep){
        return div {
            id = "performance-input-container"
            form {
                attributes["hx-ext"] = "json-enc"
                attributes["hx-post"] = "/workouts/performances"
                attributes["hx-target"] = "#performance-input-container"
                attributes["hx-swap"] = "outerHTML"

                // Скрытое поле с названием
                input(type = InputType.hidden) {
                    name = "exerciseName"
                    value = task.exerciseName
                }

                h3 {+"""
                        Задание : ${task.exerciseName} 
                        вес ${ task.weight } кг
                        на ${ task.reps } повторов
                    """.trimIndent()
                }
                br {}
                label { +"Введи выполненный результат упражнения" }
                br {}
                br {}
                label { +"Выполнено:" }
                br {}
                label { +"Вес (кг):" }
                input(type = InputType.number) {
                    name = "weight"
                    value = "10.0"
                    step = "0.5"
                }

                label { +"Повторения:" }
                input(type = InputType.number) {
                    name = "reps"
                    value = "15"
                }

                button {
                    type = ButtonType.submit
                    +"Сохранить подход"
                }
            }
        }
    }
}