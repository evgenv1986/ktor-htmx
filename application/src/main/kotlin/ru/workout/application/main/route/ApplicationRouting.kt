package ru.workout.application.main.route

import io.ktor.server.routing.Routing

fun Routing.registerAllRoutes() {
    completeStepRoute()
    completeInputRepsRoute()
//    planWorkoutRoutes()
//    submitWorkoutRoutes()
//    beginWorkoutRoutes()
    completeRepsHandleRouteConfig()
}