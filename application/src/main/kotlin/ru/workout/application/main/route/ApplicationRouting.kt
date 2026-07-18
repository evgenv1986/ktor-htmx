package ru.workout.application.main.route

import io.ktor.server.routing.Routing
import ru.workout.application.main.entrySet.entrySetConfig

fun Routing.registerAllRoutes() {
    completeStepRoute()
    logExerciseSetInputRoute()
//    planWorkoutRoutes()
//    submitWorkoutRoutes()
//    beginWorkoutRoutes()
    logExerciseSetHandleRouteConfig()
//    entrySetConfig()
}