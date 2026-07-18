package ru.workout.application.main.route

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.html.respondHtml
import io.ktor.server.request.receive
import io.ktor.server.routing.Routing
import io.ktor.server.routing.put
import kotlinx.html.FlowContent
import kotlinx.html.body
import kotlinx.html.div
import kotlinx.html.style


import ru.workout.rest.COMPLETION_REP

//todo: Упростить маршруты: создание маршрута, регистрация, вызов
fun Routing.logExerciseSetHandleRouteConfig() {
    LogExerciseSetHandleRoute(
        routing = this,
        LogExerciseSetHandleEndpoint(
            LogExerciseSetHandleView()
        )
    ).register()
}

class LogExerciseSetHandleRoute(
    private val routing: Routing,
    private val completeRepsHandleEndpoint: LogExerciseSetHandleEndpoint
) {
    fun register() {
        routing.put(COMPLETION_REP) {
            completeRepsHandleEndpoint.handle(call)
        }
    }
}
class LogExerciseSetHandleEndpoint(
    val completeRepsHandleView: LogExerciseSetHandleView
) {
    suspend fun handle(call: ApplicationCall) {
        val completion = call.receive<ExerciseSetInputRequest>()
        call.response.status(HttpStatusCode.Created)
        call.respondHtml(HttpStatusCode.Created) {
            body {
                with(completeRepsHandleView) {
                    show(completion)
                }
            }
        }
    }
}

class LogExerciseSetHandleView {
    fun FlowContent.show(completion: ExerciseSetInputRequest) {
        div {
            style = "color: green; font-weight: bold;"
            +"✅ Повторения сохранены:"
            +" упражнение: ${completion.exerciseName}"
            + " количество повторений: ${completion.reps}";
        }
    }

}
