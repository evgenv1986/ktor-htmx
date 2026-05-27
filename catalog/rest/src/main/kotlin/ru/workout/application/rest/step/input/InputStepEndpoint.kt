package ru.workout.application.rest.step.input

import io.ktor.server.application.ApplicationCall
import io.ktor.server.html.respondHtml
import kotlinx.html.body
import ru.workout.application.rest.task.TaskOfStepById
import kotlin.text.toIntOrNull

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