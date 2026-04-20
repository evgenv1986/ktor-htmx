package com.example.rest.executionStep.input

import com.example.rest.executionStep.task.TaskOfStepById
import io.ktor.server.application.ApplicationCall
import io.ktor.server.html.respondHtml
import kotlinx.html.body

class InputStepEndpoint(private val inputStepPerformView: InputStepPerformView) {
    // Логика отображения формы
    suspend fun handle(call: ApplicationCall) {
        val setId = call.parameters["setId"]?.toIntOrNull()
        val task = TaskOfStepById(setId).invoke()
        call.respondHtml {
            body {
                with(inputStepPerformView) {
                   invoke(task)
                }
            }
        }
    }
}