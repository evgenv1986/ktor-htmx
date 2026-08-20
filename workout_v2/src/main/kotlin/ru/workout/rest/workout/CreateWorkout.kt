package ru.workout.application.ru.workout.rest.workout

import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.html.respondHtml
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import kotlinx.html.body
import kotlinx.html.div
import kotlinx.html.id
import kotlinx.html.p
import ru.workout.application.ru.workout.domain.workout.WorkoutId2
import java.util.UUID

fun Route.createWorkout(){
    get("/workouts/creation/new"){
        CreateWorkout().handle(call)
    }
}
class CreateWorkout {
    suspend fun handle(call: ApplicationCall) {
        val workoutId = WorkoutId2(UUID.randomUUID())
        val workoutIdString = workoutId.value.toString()
        call.respondHtml {
            body {
                div {
                    id = workoutIdString
                    p { +"Создание тренировки" }
                }}}
    }
}