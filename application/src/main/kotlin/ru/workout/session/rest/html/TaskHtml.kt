package ru.workout.session.rest.html

import io.ktor.server.application.ApplicationCall
import kotlinx.html.FlowContent
import kotlinx.html.div
import kotlinx.html.id
import kotlinx.html.p

class TaskHtml(val call: ApplicationCall) {
    fun render(content: FlowContent, response: TaskResponse) {
        content.div {
            id = "setContainer"
            p {
                +"задание для тренировки setId: ${response.setId}"
            }
        }
    }
}