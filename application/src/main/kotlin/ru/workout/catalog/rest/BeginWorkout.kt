package ru.workout.catalog.rest

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import io.ktor.server.routing.put
import ru.workout.rest.BEGIN_WORKOUT

class BeginWorkoutRoute(
    private val routing: Routing,
    private val beginWorkoutEndPoint: BeginWorkoutEndPoint
) {
    fun register() {
        routing.put(BEGIN_WORKOUT) {
            beginWorkoutEndPoint.handle(call)
        }
    }
}

class BeginWorkoutEndPoint(
    private val beginWorkoutUseCase: IBeginWorkoutUseCase
) {
    suspend fun handle(call: ApplicationCall) {
        val sessionId = call.receive<String>()
        beginWorkoutUseCase.invoke(sessionId)
        call.respond(HttpStatusCode.Created)
    }
}

interface IBeginWorkoutUseCase {
    fun invoke(sessionId: String)
}
class BeginWorkoutUseCase: IBeginWorkoutUseCase {
    override fun invoke(sessionId: String){

    }
}
