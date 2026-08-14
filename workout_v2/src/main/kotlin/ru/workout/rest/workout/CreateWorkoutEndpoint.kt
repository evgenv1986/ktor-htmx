package ru.workout.application.ru.workout.rest.workout

import io.ktor.server.application.call
import io.ktor.server.html.respondHtml
import io.ktor.server.request.receive
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import kotlinx.html.body
import kotlinx.html.div
import kotlinx.html.id
import kotlinx.html.p

fun Route.createWorkout(){
    post ("/workouts/{workoutId}/creation") {
        val workoutId = call.parameters["workoutId"]!!
        val workoutRequest = call.receive<WorkoutCreationRequest>()
        call.respondHtml {
            body {
                div {
                    id = workoutId
                    p { workoutRequest.toString() }
                }}}
    }
}

class CreateWorkoutEndpoint {

}