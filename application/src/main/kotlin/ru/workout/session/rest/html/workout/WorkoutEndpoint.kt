package ru.workout.session.rest.html.workout

import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.html.respondHtml
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import kotlinx.html.body
import ru.workout.session.rest.html.TaskResponse
import ru.workout.session.rest.html.workout.WorkoutHtml

fun Route.workoutRoutes() {
    get("/workouts/{workoutId}") {
        WorkoutByIdEndpoint(call).handle(call)
    }
//    post(""){
//        HandleAddSetEndpoint(call).handle()
//    }
}
class WorkoutByIdEndpoint(
    val call: ApplicationCall
) {
    suspend fun handle(call: ApplicationCall) {
        val workoutId = call.parameters["workoutId"]!!
        call.respondHtml {
            body {
                with(WorkoutHtml(call)) {
                    render(
                        this@body,
                        WorkoutResponse(workoutId))
                }
            }
        }
    }
}