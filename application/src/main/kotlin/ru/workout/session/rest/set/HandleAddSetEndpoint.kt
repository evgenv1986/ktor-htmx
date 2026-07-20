package ru.workout.session.rest.set

import io.ktor.server.application.ApplicationCall
import io.ktor.server.html.respondHtml
import io.ktor.server.request.receiveParameters
import kotlinx.html.body
import kotlinx.html.id
import kotlinx.html.p

class HandleAddSetEndpoint(
    val call: ApplicationCall
) {
    suspend fun handle() {
        val form = call.receiveParameters()
        val reps = form["reps"]
        call.respondHtml {
            body {
                p {
                    id = "progress-info"
                    +"Выполнено: ${reps}"
                }
            }
        }
    }
}