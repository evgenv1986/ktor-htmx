package ru.workout.application.main.route

import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.TextContent
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.html.respondHtml
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import kotlinx.html.FlowContent
import kotlinx.html.body
import kotlinx.html.div
import kotlinx.html.style


import ru.workout.rest.COMPLETION_REP

fun Routing.completeRepsHandleRouteConfig() {
    CompleteRepsHandleRoute(
        routing = this,
        CompleteRepsHandleEndpoint(
            CompleteRepsHandleView()
        )
    ).register()
}

class CompleteRepsHandleRoute(
    private val routing: Routing,
    private val completeRepsHandleEndpoint: CompleteRepsHandleEndpoint
) {
    fun register() {
        routing.put(COMPLETION_REP) {
            completeRepsHandleEndpoint.handle(call)
        }
    }
}
class CompleteRepsHandleEndpoint(
    val completeRepsHandleView: CompleteRepsHandleView
) {
    suspend fun handle(call: ApplicationCall) {
        val completion = call.receive<CompleteRepsInputRequest>()
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

class CompleteRepsHandleView {
    fun FlowContent.show(completion: CompleteRepsInputRequest) {
        div {
            style = "color: green; font-weight: bold;"
            +"✅ Повторения сохранены:"
            +" упражнение: ${completion.exerciseName}"
            + " количество повторений: ${completion.reps}";
        }
    }

}
