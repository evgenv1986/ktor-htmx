package ru.workout.application.main.route

import io.ktor.server.routing.Routing
import ru.workout.catalog.rest.CompleteStepEndPoint
import ru.workout.catalog.rest.CompleteStepRoute
import ru.workout.catalog.rest.MockCompleteStepUseCase

fun Routing.completeStepRoute() {
    CompleteStepRoute(
        routing = this,
        CompleteStepEndPoint(
            MockCompleteStepUseCase()
        )
    ).register()
}