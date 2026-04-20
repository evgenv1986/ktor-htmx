package com.example.rest.executionStep.input

import io.ktor.server.application.call
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get

class InputStepRoute(private val routing: Routing) {
    fun register() {
        routing.get("/workouts/performances/{setId}") {
            InputStepEndpoint().handle(call)
        }
    }
}