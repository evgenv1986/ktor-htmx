package ru.workout.catalog.rest

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import io.ktor.server.routing.put
import kotlinx.serialization.Serializable
import ru.workout.rest.COMPLETE_STEP

class CompleteStepRoute(
    private val routing: Routing,
    private val CompleteStepEndPoint: CompleteStepEndPoint
) {
    fun register() {
        routing.put(COMPLETE_STEP) {
            CompleteStepEndPoint.handle(call)
        }
    }
}

class CompleteStepEndPoint(
//    private val beginWorkoutUseCase: IBeginWorkoutUseCase
) {
    suspend fun handle(call: ApplicationCall) {
        val stepRequest = call.receive<CompleteStepRequest>()
        val stepId = call.parameters["stepId"]
//        val stepResponse = StepResponse(
//             stepId = "stepId1"
//        )
//        beginWorkoutUseCase.invoke(sessionId)
        call.respond(
            HttpStatusCode.Created,
            mapOf("step completed successfully" to stepId)
        )
    }
}

@Serializable
data class StepCompleteResponse(val stepId: String) {

}

@Serializable
data class CompleteStepRequest(
    val actualReps: Int
) {
    val stepId: Int? = null
}