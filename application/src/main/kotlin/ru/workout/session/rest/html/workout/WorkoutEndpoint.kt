package ru.workout.session.rest.html.workout

import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.html.respondHtml
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import kotlinx.html.body

fun Route.workoutRoutes() {
    get("/workouts/{workoutId}/task") {
        WorkoutTaskByIdEndpoint(call).handle(call)
    }
//    post(""){
//        HandleAddSetEndpoint(call).handle()
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
        val workout = WorkoutResponse(
            workoutId,
            sets
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