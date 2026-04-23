package com.example.rest.executionStep.main.persist

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.html.respondHtml
import io.ktor.server.routing.Routing
import io.ktor.server.routing.post
import kotlinx.html.body

class StepCompleteRoute(
    private val routing: Routing,
    private val stepCompleteEndpoint: StepCompleteEndpoint
) {
    fun register() {
        routing.post("/workouts/performances") {
            try {
                with(stepCompleteEndpoint){
                    handle(call)
                }
            } catch (e: Exception) {
                call.respondHtml(HttpStatusCode.Companion.BadRequest) {
                    body { +"Ошибка: ${e.message}" }
                }
            }
        }
    }
}