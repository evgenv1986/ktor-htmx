package com.example.rest.executionStep.main.step

import com.example.rest.executionStep.main.input.InputStepEndpoint
import io.ktor.server.application.call
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get

class InputStepRoute(
    private val routing: Routing,
    private val inputStepEndpoint: InputStepEndpoint
) {
    fun register() {
        routing.get("/workouts/performances/{setId}") {
            inputStepEndpoint.handle(call)
        }
    }
}