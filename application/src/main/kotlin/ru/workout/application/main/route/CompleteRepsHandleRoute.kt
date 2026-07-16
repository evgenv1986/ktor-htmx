package ru.workout.application.main.route

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.html.respondHtml
import io.ktor.server.routing.Routing
import io.ktor.server.routing.post
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
        routing.post(COMPLETION_REP) {
            completeRepsHandleEndpoint.handle(call)
        }
    }
}
class CompleteRepsHandleEndpoint(
    val completeRepsHandleView: CompleteRepsHandleView
) {
    suspend fun handle(call: ApplicationCall) {
        call.respondHtml {
            HttpStatusCode.Created
            body {
                with(completeRepsHandleView) {
                    show()
                }
            }
        }
    }
}

class CompleteRepsHandleView {
    fun FlowContent.show() {
        div {
            style = "color: green; font-weight: bold;"
            +"✅ Повторения сохранены: "
            +"тут вставить название упражнения: ${
                "и вставить количество повторений"
            }, "
        }
    }

}
