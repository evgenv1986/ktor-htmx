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


class WorkoutStepCreateRoute(private val routing: Routing) {
    fun register() {
        routing.get("/workouts/performances/{setId}") {
//            println("URI: ${call.request.uri}")
//            val setId = call.parameters["setId"]?.toIntOrNull()
//            val task = ExtractStepById(setId).invoke()
            WorkoutStepCreateEndpoint().handle(call)
        }
    }
}
class WorkoutStepCreateEndpoint {
    // Логика отображения формы
    suspend fun handle(call: ApplicationCall) {
        val setId = call.parameters["setId"]?.toIntOrNull()
        val task = ExtractStepById(setId).invoke()
        with(InputExerciseStepPerformView(call)) {
            invoke(task)
        }
    }
}
class InputExerciseStepPerformView(val call: ApplicationCall) {
    suspend fun invoke(task: TaskOfStep) {
        call.respondHtml {
            body {
                content(task)

            }
        }
    }
    fun FlowContent.content(task: TaskOfStep){
        return div {
            id = "performance-input-container"
            form {
                attributes["hx-ext"] = "json-enc"
                attributes["hx-post"] = "/workouts/performances"
                attributes["hx-target"] = "#performance-input-container"
                attributes["hx-swap"] = "outerHTML"

                // Скрытое поле с названием
                input(type = InputType.hidden) {
                    name = "exerciseName"
                    value = "Подтягивания"
                }

                h3 {+"""
                        Задание : ${task.exerciseName} 
                        вес ${ task.weight } кг
                        на ${ task.reps } повторов
                    """.trimIndent()
                }
                br {}
                label { +"setId: ${task}" }
                br {}
                label { +"Введи выполненный результат упражнения" }
                br {}
                br {}
                label { +"Выполнено:" }
                br {}
                label { +"Вес (кг):" }
                input(type = InputType.number) {
                    name = "weight"
                    value = "10.0"
                    step = "0.5"
                }

                label { +"Повторения:" }
                input(type = InputType.number) {
                    name = "reps"
                    value = "15"
                }

                button {
                    type = ButtonType.submit
                    +"Сохранить подход"
                }
            }
        }
    }
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
