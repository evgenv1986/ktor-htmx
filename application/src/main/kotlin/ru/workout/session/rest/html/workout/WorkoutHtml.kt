package ru.workout.session.rest.html.workout

import io.ktor.server.application.ApplicationCall
import kotlinx.html.FlowContent
import kotlinx.html.div
import kotlinx.html.id
import kotlinx.html.p

class WorkoutHtml(val call: ApplicationCall) {
    fun render(content: FlowContent, response: WorkoutResponse) {
        content.div {
            id = "workoutId"
            p {
                +"тренировка workoutId: ${response.workoutId}"
            }
        }
    }

}