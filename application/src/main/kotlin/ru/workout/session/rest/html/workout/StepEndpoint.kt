package ru.workout.session.rest.html.workout

import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.html.respondHtml
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import kotlinx.html.body

fun Route.stepRoutes() {
    get("/workouts/{workoutId}/sets/{setId}/rounds/{roundId}/steps/{stepId}/completion") {
        StepCompletionEndpoint(call).handle()
    }
//    get("/workouts/{workoutId}/sets/{setId}/rounds/{roundId}/steps/{stepId}/completion/new") {
//        StepCompletionEndpoint(call).handle()
//    }
//    post("/steps/{stepId}/completion"){
//        CompletionStepEndpoint(call).handle()
//    }
}

class StepCompletionEndpoint(val call: ApplicationCall) {
    suspend fun handle() {
        val stepCompletion = StepCompletionResponse(
            "step-1",
            RepsResponse(35))
        call.respondHtml {
            body {
                with(StepCompletionHtml(call)) {
                    render(this@body,
                        listOf(stepCompletion))
                }
            }
        }
    }
}