package ru.workout.catalog.rest

import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.routing.Routing
import io.ktor.server.routing.put
import kotlinx.serialization.Serializable
import ru.workout.rest.COMPLETE_STEP
import ru.workout.session.domain.SessionStep
import ru.workout.session.usecase.ICompleteStepUseCase

class CompleteStepRoute(
    private val routing: Routing,
    private val completeStepEndPoint: CompleteStepEndPoint
) {
    fun register() {
        routing.put(
            COMPLETE_STEP) {
            completeStepEndPoint.handle(call)
        }
    }
}

class CompleteStepEndPoint(
    private val completeStepUseCase: ICompleteStepUseCase
) {
    suspend fun handle(call: ApplicationCall) {
        val stepRequest = call.receive<CompleteStepRequest>()
        val requestStepTemplateId = call.parameters["stepTemplateId"] !!

//        val validatedStepTemplateId: StepTemplateId = ValidateStepTemplateId(requestStepTemplateId).StepTemplateId()
//        val validatedSessionId: SessionId = ValidatedSessionId(stepRequest.sessionId).SessionId()
//        val validatedActualReps: ActualReps = ValidatedActualReps(validatedActualReps).ActualReps()
//
//        val completeStepCommand = CompleteStepCommand(
//            validatedStepTemplateId,
//            validatedSessionId,
//            validatedActualReps
//        )

//        val stepId = completeStepUseCase.invoke(completeStepCommand)

//        val response = StepCompletionResponse.from(
//            completeStepCommand,
//            stepId
//        )
//        call.respond(
//            HttpStatusCode.Created,
//            response
//        )
    }
}




class MockCompleteStepUseCase: ICompleteStepUseCase {
    override fun invoke(completeStepCommand: CompleteStepCommand) {
        TODO("Not yet implemented")
    }
}

class CompleteStepUseCase: ICompleteStepUseCase {
    override fun invoke(completeStepCommand: CompleteStepCommand) {
        TODO("Not yet implemented")
    }
}

private fun SessionStep.toResponse(): StepCompletionResponse {
    return StepCompletionResponse(
        stepId,
        status.toString(),
        actualReps,
        "sessionId-1"
    )
}

@Serializable
data class StepCompletionResponse(
    val stepId: String,
    val status: String,
    val actualReps: Int,
    val sessionId: String
) {
    companion object {
        fun from(completeStepCommand: CompleteStepCommand, stepId: Unit) {
            TODO()
        }
//            StepCompletionResponse(
//            stepId,
//            step.status.toString(),
//            step.actualReps
//        )
    }
}

@Serializable
data class CompleteStepRequest(
    val actualReps: Int,
    val sessionId: String,
    val stepTemplateId: String
)

class CompleteStepCommand(
    validatedStepTemplateId: Any,
    validatedSessionId: Any,
    validatedActualReps: Any
) {}
