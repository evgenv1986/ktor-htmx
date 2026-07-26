package ru.workout.session.rest.task

import io.ktor.server.application.ApplicationCall
import io.ktor.server.html.respondHtml
import kotlinx.html.FlowContent
import kotlinx.html.body
import kotlinx.html.div
import kotlinx.html.id
import kotlinx.html.p
import kotlinx.serialization.Serializable
import javax.management.Query.div

class WorkoutHtml(val call: ApplicationCall) {
    fun render(content: FlowContent, response: TaskResponse) {
        content.div {
            id = "workoutId"
            p {
                +"задание для тренировки workoutId: ${response.workoutId}"
            }
        }
    }

}