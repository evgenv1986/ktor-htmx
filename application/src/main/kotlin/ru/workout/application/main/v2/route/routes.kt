package ru.workout.application.main.v2.route

import io.ktor.server.application.call
import io.ktor.server.html.respondHtml
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import kotlinx.html.body
import kotlinx.html.h1

fun Route.application_v2() {
    get("/workouts/v2"){
        call.respondHtml {
            body {
                h1 {+"Тренировки"}
            }
        }
    }
}