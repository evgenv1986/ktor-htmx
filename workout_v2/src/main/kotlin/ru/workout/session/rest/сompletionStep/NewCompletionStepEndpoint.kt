package ru.workout.application.ru.workout.session.rest.stepCompletion

import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.html.respondHtml
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import kotlinx.html.ButtonType
import kotlinx.html.InputType
import kotlinx.html.body
import kotlinx.html.button
import kotlinx.html.div
import kotlinx.html.form
import kotlinx.html.h3
import kotlinx.html.id
import kotlinx.html.input
import kotlinx.html.span
import kotlinx.html.style
import kotlinx.serialization.Serializable

fun Route.completionStepRoute(){
    post("/steps/{stepId}/completion"){
        call.respondText("✅ Шаг request.stepId выполнен: request.reps повторений")
    }
}
fun Route.newCompletionStepEndpoint(){
    get ("/steps/{stepId}/completion/new") {
        val stepId = call.parameters["stepId"]!!
        val stepCompletionNew = StepCompletionNew(
            id = stepId,
            exerciseName = "mock-exercise-name",
            targetReps = 35
        )
        NewCompletionStepEndpoint().invoke(call, stepCompletionNew)
    }
}
@Serializable
class StepCompletionNew(
    val id: String,
    val exerciseName: String,
    val targetReps: Int
)
class NewCompletionStepEndpoint {
    suspend fun invoke(call: ApplicationCall, step: StepCompletionNew){
        call.respondHtml {
            body {
                form {
                    style = "cursor: pointer; border: 1px solid #ddd; padding: 16px; margin: 8px 0; border-radius: 8px;"
                    attributes["hx-post"] = "/steps/${step.id}/completion"
                    attributes["hx-trigger"] = "click"
                    attributes["hx-target"] = "#status-${step.id}"
                    attributes["hx-swap"] = "innerHTML"
                    input(type = InputType.number, name = "reps") {
                        placeholder = "Количество повторений"
                        required = true
                        min = "1"
                        width = "1"
                    }
                    button(type = ButtonType.submit) { +"Выполнил" }

                    h3 { +step.exerciseName }

                    span {
                        id = "badge"
                        +"Нажмите, чтобы выполнить"
                    }
                    div {
                        id = "status-${step.id}"
                        style = "margin-bottom: 16px;"
                    }
                    div {
                        span { +"это обработчик маршрута формы ввода повторений шага - /steps/{stepId}/completion/new" }
                    }
                }
            }
        }
    }
}