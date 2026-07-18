package ru.workout.session.rest.set

import io.ktor.http.HttpStatusCode
import kotlinx.html.FlowContent
import io.ktor.server.application.ApplicationCall
import io.ktor.server.html.respondHtml
import kotlinx.html.body
import kotlinx.html.div
import kotlinx.html.style

class EntrySetForm(val call: ApplicationCall) {
    suspend fun view(taskId: String?) {
        call.respondHtml(HttpStatusCode.OK) {
            body {
                render(taskId)
            }
        }
    }
    fun FlowContent.render(taskId: String?) {
        div {
            style = "color: green; font-weight: bold;"
            +"Вы работаете с задачей taskId = $taskId"
            +"✅ Повторения сохранены:"
            +" упражнение: completion.exerciseName"
            + " количество повторений: completion.reps";
        }

    }
}