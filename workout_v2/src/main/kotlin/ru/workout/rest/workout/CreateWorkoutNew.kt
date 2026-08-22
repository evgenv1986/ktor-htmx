package ru.workout.application.ru.workout.rest.workout

import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.html.respondHtml
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import kotlinx.html.ButtonType
import kotlinx.html.InputType
import kotlinx.html.body
import kotlinx.html.br
import kotlinx.html.button
import kotlinx.html.div
import kotlinx.html.form
import kotlinx.html.head
import kotlinx.html.id
import kotlinx.html.input
import kotlinx.html.p
import kotlinx.html.script
import kotlinx.html.span
import ru.workout.application.ru.workout.domain.workout.WorkoutId2
import java.util.UUID

fun Route.createWorkoutNew(){
    get("/workouts/creation/new"){
        CreateWorkoutNew().handle(call)
    }
    get("/workouts/creation/new-name"){
        val workoutId = call.parameters["workout_id"].toString()
        call.respondHtml {
            body {
                div(classes = "workoutName") {
                    form {
                        attributes["hx-post"] = "/workouts/creation"
                        attributes["hx-target"] = "#workout-name"
                        attributes["hx-swap"] = "innerHTML"

                        input {
                            type = InputType.hidden
                            id = "workout-id"
                            name = "workout_id"
                            value = workoutId
                        }
                        br {}
                        input {
                            type = InputType.text
                            id = "workout-name-input"
                            name = "workout_name"
                            placeholder = "Введите название тренировки"
                        }
                        br {}
                        button(type = ButtonType.submit) {
                            +"Сохранить название"
                        }
                        button {
                            type = ButtonType.button
                            attributes["hx-get"] = "/workouts/creation/cancel-name"
                            attributes["hx-target"] = "#workout-name"
                            attributes["hx-swap"] = "innerHTML"
                            +"Отмена"
                        }
                    }
                }
            }
        }
    }
}
//fun Route.createWorkoutHandle(){
//    post.
//}
class CreateWorkoutNew() {
    suspend fun handle(call: ApplicationCall) {
        val workoutId = WorkoutId2(UUID.fromString("2ab04ceb-6de3-4195-a0cb-45826ccb8a6e"))
        val workoutIdString = workoutId.value.toString()
        call.respondHtml {
            head {
                script { src = "https://unpkg.com/htmx.org@1.9.12" }
                script { src = "https://unpkg.com/htmx.org/dist/ext/json-enc.js" }
            }
            body {
                div {
                    id = "workout"
                    form {
                        attributes["hx-post"] = "/workouts/creation"
                        attributes["hx-target"] = "#workoutCreation"
                        attributes["hx-swap"] = "outerHTML"

                        input {
                            type = InputType.hidden
                            id = "workout-id"
                            name = "workout_id"
                            value = workoutIdString
                        }

                        p { +"Создание тренировки" }
                        span { +"workoutId: $workoutIdString" }
                        br {}
                        div {
                            id = "workout-name"
                        }
                        button{
                            attributes["hx-get"] = "/workouts/creation/new-name?workout_id=$workoutIdString"
                            attributes["hx-target"] = "#workout-name"
                            attributes["hx-swap"] = "innerHTML"
                            attributes["type"] = "button"
                            +"Добавить название тренировки"
                        }
                        div {
                            id = "exercises"
                        }
                        button(type = ButtonType.submit) {
                            +"Сохранить тренировку"
                        }
                    }
                }
            }
        }
    }
}