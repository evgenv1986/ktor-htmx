package com.example.rest.executionStep.main.workout

import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.html.respondHtml
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import kotlinx.html.ButtonType
import kotlinx.html.FlowContent
import kotlinx.html.body
import kotlinx.html.br
import kotlinx.html.button
import kotlinx.html.div
import kotlinx.html.form
import kotlinx.html.h3
import kotlinx.html.id
import kotlinx.html.textArea
import kotlinx.serialization.Serializable

class WorkoutInputRoute(
    private val routing: Routing,
    private val workoutInputEndpoint: WorkoutInputEndpoint
) {
    fun register() {
        routing.get("/workouts/plannings/add") {
            workoutInputEndpoint.handle(call)
        }
    }
}
class WorkoutInputEndpoint(
    val workoutInputView: WorkoutInputView) {
    suspend fun handle(call: ApplicationCall) {
        val workoutInput = WorkoutInput()
        call.respondHtml {
            body {
                with(workoutInputView) {
                    invoke(workoutInput)
                }
            }
        }
    }
}
class WorkoutInputView {
    fun FlowContent.invoke(workoutInput: WorkoutInput){
        return div {
            id = "performance-input-container"
            form {
                attributes["hx-ext"] = "json-enc"
                attributes["hx-post"] = "/workouts/plannings/add"
                attributes["hx-target"] = "#performance-input-container"
                attributes["hx-swap"] = "outerHTML"

                h3 {+"""
                        Введите текст тренировки
                    """.trimIndent()
                }
                br {}
                textArea {
                    name = "workoutText"
                    rows = "5"
                    cols = "80"
                    placeholder = "Введите задание тренировки"
                    required = true
                    id = "workout-text-input"
                }
                button {
                    type = ButtonType.submit
                    +"Сохранить тренировку"
                }
            }
        }
    }
}
@Serializable
data class WorkoutInput(
    val workoutText: String = ""
)