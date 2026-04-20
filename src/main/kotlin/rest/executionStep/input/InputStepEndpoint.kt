package com.example.rest.executionStep.input

import com.example.ExtractStepById
import io.ktor.server.application.ApplicationCall

class InputStepEndpoint {
    // Логика отображения формы
    suspend fun handle(call: ApplicationCall) {
        val setId = call.parameters["setId"]?.toIntOrNull()
        val task = ExtractStepById(setId).invoke()
        with(InputStepPerformView(call)) {
            invoke(task)
        }
    }
}