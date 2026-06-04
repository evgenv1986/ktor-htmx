package ru.workout.catalog.rest

import arrow.core.Either
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.request.receive
import io.ktor.server.routing.Routing
import io.ktor.server.routing.post
import kotlinx.html.FlowContent
import kotlinx.html.style
import io.ktor.server.application.call
import io.ktor.server.html.respondHtml
import io.ktor.server.response.respond
import kotlinx.html.body
import kotlinx.html.div
import ru.workout.catalog.domain.WorkoutId
import ru.workout.rest.WORKOUT_PLANS
import ru.workout.session.usecase.WorkoutPlan
import ru.workout.session.usecase.PlanWorkoutUseCase
import ru.workout.session.usecase.WorkoutUseCaseError

class SubmitWorkoutPlanRoute(
    private val routing: Routing,
    private val submitWorkoutPlanEndPoint: SubmitWorkoutPlanEndPoint
) {
    fun register() {
        routing.post(WORKOUT_PLANS) {
            submitWorkoutPlanEndPoint.handle(call)
        }
    }
}
class SubmitWorkoutPlanEndPoint(
    val planWorkoutUseCase: PlanWorkoutUseCase,
    val workoutPlanView: WorkoutPlanView) {
    suspend fun handle(call: ApplicationCall) {
        val input = call.receive<WorkoutPlan>()
        val validateExercises = ValidWorkout(
            input,
            "exercises"
        ).exercises()
        validateExercises.fold(
            ifLeft = { error ->
                toInvalidParamsUnprocessableEntity(call, error)
            },
            ifRight = {
                handleUseCaseResult(
                    call,
                    planWorkoutUseCase.invoke(it)
                )
            }
        )
    }

    private suspend fun toInvalidParamsUnprocessableEntity(
        call: ApplicationCall,
        error: ValidationError
    ) {
        call.respond(HttpStatusCode.UnprocessableEntity, "${error.field}: ${error.message}")
    }

    private suspend fun handleUseCaseResult(
        call: ApplicationCall,
        result: Either<WorkoutUseCaseError, WorkoutId>
    ) = result.fold(
            ifLeft = { useCaseError ->
                call.respond(
                    HttpStatusCode.UnprocessableEntity,
                "error: ${useCaseError.toRestError()}"
                )
            },
            ifRight = { workoutId ->
                call.respondHtml {
                    HttpStatusCode.Created
                    body {
                        with(workoutPlanView) {
                            invoke(workoutId.toResponse())
                        }
                    }
                }
            }
    )
    private suspend fun toInvalidParamsBadRequest(call: ApplicationCall, error: ValidationError) {
        call.respond(HttpStatusCode.BadRequest, "${error.field}: ${error.message}")
    }
}
private fun WorkoutUseCaseError.toRestError() = when(this) {
    WorkoutUseCaseError.AlreadyExist ->
        WorkoutErrorResponse(
        WorkoutRestError.WORKOUT_ALREADY_EXISTS).error
    WorkoutUseCaseError.EmptyWorkoutUseCase ->
        WorkoutErrorResponse(
            WorkoutRestError.EMPTY_WORKOUT)
}
class WorkoutErrorResponse(val error: WorkoutRestError)
enum class WorkoutRestError {
    WORKOUT_ALREADY_EXISTS,
    EMPTY_WORKOUT
}
private fun WorkoutId.toResponse(): String {
    return value.toString()
}

class WorkoutPlanView {
    fun FlowContent.invoke(workoutId: String) {
        div {
            style = "color: green; font-weight: bold;"
            +"✅ Тренировка сохранена: "
            +"id тренировки: ${
                workoutId
            }, "
        }
    }
}
//@Serializable
//data class WorkoutAddHandleModel()