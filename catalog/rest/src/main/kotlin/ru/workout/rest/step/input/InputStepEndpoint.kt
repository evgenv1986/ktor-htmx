package ru.workout.rest.step.input

import io.ktor.server.application.ApplicationCall
import io.ktor.server.html.respondHtml
import kotlinx.html.body
import kotlin.text.toIntOrNull
import ru.workout.rest.task.TaskOfStepById

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