package com.example

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.application.call
import io.ktor.server.html.respondHtml
import io.ktor.server.request.receive
import io.ktor.server.routing.*
import kotlinx.html.*
import kotlinx.serialization.Serializable

@Serializable
data class ExerciseStep(
    val exerciseName: String,
    val weight: Double,
    val reps: Int
)
@Serializable
class TaskOfStep(
    val exerciseName: String,
    val weight: Double,
    val reps: Int
) {

}


class WorkoutStepCompleteRoute(
    private val routing: Routing
) {
    fun register() {
        routing.post("/workouts/performances") {
            try {
                with(WorkoutStepCompleteEndpoint()){
                    handle(call)
                }
            } catch (e: Exception) {
                call.respondHtml(HttpStatusCode.BadRequest) {
                    body { +"Ошибка: ${e.message}" }
                }
            }
        }
    }
}
class WorkoutStepCompleteEndpoint {
    // Логика сохранения
    suspend fun handle(call: ApplicationCall) {
        val step = call.receive<ExerciseStep>()
        with (SuccessCompleteStepViewResult(call)){
            invoke(step)
        }
    }
}
class SuccessCompleteStepViewResult(val call: ApplicationCall) {
    suspend fun invoke(step: ExerciseStep) {
        call.respondHtml {
            body {
                successMessage(step)
            }
        }
    }
    fun FlowContent.successMessage(step: ExerciseStep) {
        div {
            style = "color: green; font-weight: bold;"
               +"✅ Сохранено: "
                    +"упражнение ${step.exerciseName} "
                    +"${step.reps} раз "
                    +"по ${step.weight} кг"
        }
    }
}
class ExtractStepById(val id: Int?) {
    fun invoke(): TaskOfStep {
        return TaskOfStep(
            "Подтягивания",
            12.1,
            20
        )
    }

}
