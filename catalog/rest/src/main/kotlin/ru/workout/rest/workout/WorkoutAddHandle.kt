package ru.workout.rest.workout

import arrow.core.Either
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.html.respondHtml
import io.ktor.server.request.receive
import io.ktor.server.routing.Routing
import io.ktor.server.routing.post
import kotlinx.html.FlowContent
import kotlinx.html.body
import kotlinx.html.style
import ru.workout.catalog.usecase.workout.AddWorkoutUseCase
import io.ktor.server.application.call
import io.ktor.server.response.respond
import kotlinx.html.div
import ru.workout.catalog.usecase.workout.WorkoutUseCaseError
import workout.catalog.domain.workout.WorkoutId

class WorkoutAddHandleRoute(
    private val routing: Routing,
    private val workoutAddHandleEndPoint: WorkoutAddHandleEndPoint
) {
    fun register() {
        routing.post("/workouts/plannings/add") {
            workoutAddHandleEndPoint.handle(call)
        }
    }
}
class WorkoutAddHandleEndPoint(
    val addWorkoutUseCase: AddWorkoutUseCase,
    val workoutAddHandleView: WorkoutAddHandleView) {
    suspend fun handle(call: ApplicationCall) {
        val input = call.receive<WorkoutInput>()
        val validateExercises = ValidWorkout(input, "exercises").exercises()
        validateExercises.fold(
            ifLeft = { error ->
                toInvalidParamsBadRequest(call, error)
            },
            ifRight = {
                handleUseCaseResult(
                    call,
                    addWorkoutUseCase.invoke(it)
                )
            }
        )
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
                        with(workoutAddHandleView) {
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

class WorkoutAddHandleView {
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