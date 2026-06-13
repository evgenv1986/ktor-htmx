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
import ru.workout.session.domain.SessionStep
import ru.workout.session.domain.StepStatus

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
        val stepId = call.parameters["stepId"] !!
        val step = SessionStep(
            stepId,
            stepRequest.actualReps,
            StepStatus.COMPLETED

            )

        val response = StepCompletionResponse.from(step)
        call.respond(
            HttpStatusCode.Created,
//            mapOf("step" to response)
            response
        )
    }
}

private fun SessionStep.toResponse(): StepCompletionResponse {
    return StepCompletionResponse(
        stepId,
        status.toString(),
        actualReps
    )
}

@Serializable
data class StepCompletionResponse(
    val stepId: String,
    val status: String,
    val actualReps: Int
) {
    companion object {
        fun from(step: SessionStep) = StepCompletionResponse(
            step.stepId,
            step.status.toString(),
            step.actualReps
        )
    }
}

@Serializable
data class CompleteStepRequest(
    val actualReps: Int
)