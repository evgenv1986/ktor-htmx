package com.example.rest.executionStep.main.workout

import io.ktor.server.application.ApplicationCall
import io.ktor.server.html.respondHtml
import io.ktor.server.request.receive
import io.ktor.server.routing.Routing
import io.ktor.server.routing.post
import kotlinx.html.FlowContent
import kotlinx.html.body
import kotlinx.html.div
import kotlinx.html.style
import workout.catalog.usecase.AddWorkoutUseCase
import io.ktor.server.application.call

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
        val workoutId = addWorkoutUseCase.invoke(input.workoutText)

        val workoutInput = WorkoutInput()
        call.respondHtml {
            body {
                with(workoutAddHandleView) {
                    invoke(input)
                }
            }
        }
    }
}
class WorkoutAddHandleView {
    fun FlowContent.invoke(input: WorkoutInput) {
        div {
            style = "color: green; font-weight: bold;"
            +"✅ Тренировка сохранена: "
            +"текст тренировки: ${input.workoutText} "
        }
    }
}
//@Serializable
//data class WorkoutAddHandleModel()