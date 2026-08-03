package ru.workout.session.rest.html.workout

import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.html.respondHtml
import io.ktor.server.request.receiveParameters
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import kotlinx.html.body
import kotlinx.html.id
import kotlinx.html.p

fun Route.workoutRoutes() {
    get("/workouts/{workoutId}/task") {
        WorkoutTaskByIdEndpoint(call).handle(call)
    }
//    post("/steps/{stepId}/completion"){
//        CompletionStepEndpoint(call).handle()
//    }
}
class WorkoutTaskByIdEndpoint(
    val call: ApplicationCall
) {
    suspend fun handle(call: ApplicationCall) {
        val workoutId = call.parameters["workoutId"]!!
        val steps = listOf<StepResponse>(
            StepResponse("step1", "pull-ups", 30),
            StepResponse("step2", "push-ups", 40),
            StepResponse("step3", "seetdown", 30),
        )
        val rounds = listOf<RoundResponse>(
            RoundResponse(roundId = "round1", steps))
        val sets = listOf<SetResponse>(
            SetResponse( "set1", rounds)
        )
        val completions = listOf<StepCompletionResponse>(
            StepCompletionResponse(
                stepId = "step1",
                RepsResponse(30)
            )
        )
        val workout = WorkoutResponse(
            workoutId,
            sets,
            completions
        )
        call.respondHtml {
            body {
                with(WorkoutHtml(call)) {
                    render(this@body, workout)
                }
            }
        }
    }
}


class CompletionStepEndpoint(val call: ApplicationCall){
    suspend fun handle(stepId: String){
        val form = call.receiveParameters()
        val reps = form["reps"].toString()
        val stepId = form["stepId"].toString()
        val response = StepCompletionResponse(stepId, RepsResponse(reps.toInt()))
        call.respondHtml {
            body {
                with(StepCompletionHtml(call)) {
                    render(this@body, listOf(response))
                }
            }
        }


    }
}