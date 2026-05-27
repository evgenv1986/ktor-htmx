package ru.workout.application.rest.step

import io.ktor.server.application.call
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import ru.workout.application.rest.step.input.InputStepEndpoint

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