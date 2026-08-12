package ru.workout.application

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.html.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.html.*
import kotlinx.serialization.json.Json
import ru.workout.application.main.entrySet.entrySetConfig
import ru.workout.session.rest.html.workout.workoutRoutes
import ru.workout.rest.COMPLETION_REPS_NEW
import ru.workout.rest.WORKOUT_PLANS_NEW
import ru.workout.session.rest.html.PresentTaskResponse
import ru.workout.session.rest.html.TaskHtml
import ru.workout.session.rest.html.TaskResponse
import ru.workout.session.rest.html.workout.WorkoutHtml
import ru.workout.session.rest.html.workout.WorkoutResponse
import ru.workout.session.rest.html.workout.stepRoutes
import ru.workout.session.usecase.task.TaskSetView
import workout.application.workout.app.UserRegistrationHandler

fun main() {
    val port = System.getenv("PORT")?.toInt() ?: 8080
    embeddedServer(Netty, port = port) {
        module()
    }.start(wait = true)
}
fun Application.module() {
    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
        })
    }
    // Создаем экземпляр нашего вынесенного класса
    val registrationHandler = UserRegistrationHandler()

    // все ниже описанные маршруты упростить по примеру выше - entrySetConfig()
    routing {
        entrySetConfig()
        workoutRoutes()
        stepRoutes()
        post ("/workouts/{workoutId}/starting") {
            val workoutId = call.parameters["workoutId"]!!
            call.respondHtml {
                body {
                    div {
                        id = workoutId.toString()
                    }}}
        }
        // получить задание из сета тренировки
        get("/workouts/{workoutId}/sets/{setId}/steps/{stepId}/task") {
            val setResponse: TaskResponse =
                PresentTaskResponse().toResponse(
                    TaskSetView(1, 2, 3, 25, "Подтягивания")
//                    .toView(TaskSet())
                )

//            call.respond(setResponse)
            call.respondHtml {
                body {
//                    WorkoutHtml(call).render(
//                        this,
//                        WorkoutResponse("1")
//                    )

                    id = "set-step-task-container"
                    TaskHtml(call).render(this, setResponse)
                    p {
                        +" Упражнение: "
                        +" ${setResponse.exerciseName}"
                        +" ${setResponse.reps} повторений"
                    }
                    div { p {  } }
                    div { p {  } }
                }
            }
        }

        var counter = 0
        // Регист`рация маршрутов из внешнего класса
        registrationHandler.registerRoutes(this)
        // маршрут формы ввода выполнения сета упражнения
        get("/") {
            call.respondHtml(HttpStatusCode.OK) {
                lang = "ru"
                head {
                    title("Ktor + HTMX")
                    meta { charset = "UTF-8" }
                    script { src = "https://unpkg.com/htmx.org@1.9.12" }
                    script { src = "https://unpkg.com/htmx.org/dist/ext/json-enc.js" }
//                        script { src = "/static/js/htmx.min.js" }
//                        script { src = "/static/js/json-enc.js" }
                    style {
                        unsafe {
                            +"""
                            body { font-family: sans-serif; max-width: 400px; margin: 40px auto; padding: 0 16px; line-height: 1.6; }
                            input { display: block; width: calc(100% - 16px); padding: 8px; margin-bottom: 10px; border: 1px solid #ccc; border-radius: 4px; }
                            button { padding: 10px 16px; background: #3b82f6; color: white; border: none; border-radius: 4px; cursor: pointer; }
                            hr { margin: 32px 0; border: none; border-top: 1px solid #e5e7eb; }
                            .form-row { display: flex; gap: 8px; align-items: center; }
                            """.trimIndent()
                        }
                    }
                }
                body {
//                        h2 { +"Ktor + HTMX MVP" }
//                        // Приветствие (старый код)
//                        div {
//                            h3 { +"1. Приветствие" }
//                            form {
//                                attributes["hx-get"] = "/hello"; attributes["hx-target"] = "#result"
//                                div("form-row") {
//                                    input(type = InputType.text) { name = "name"; value = "World" }
//                                    button { type = ButtonType.submit; +"Ок" }
//                                }
//                            }
//                            div { id = "result" }
//                        }
//                        hr {}
                    // Вызов отрисовки формы из нашего КЛАССА
//                        with(registrationHandler) {
//                            renderRegistrationForm()
//                        }
                   button {
                        attributes["hx-get"] = "/workouts/performances/123"
                        attributes["hx-target"] = "#form-inputExerciseStepPerformView"
                        +"Начать выполнение подхода №3"
                    }
                    hr{}
                    div { id = "form-inputExerciseStepPerformView" }

                    button {
                        attributes["hx-get"] = WORKOUT_PLANS_NEW
                        attributes["hx-target"] = "#form-workoutInputView"
                        +"Добавить тренировку"
                    }
                    hr{}
                    div { id = "form-workoutInputView" }

                    button {
                        attributes["hx-get"] = "$COMPLETION_REPS_NEW"
                        attributes["hx-target"] = "#completion-reps-new"
                        +"Открыть форму ввода повторов"
                    }
                    div { id = "completion-reps-new" }
                    hr{}

                    button {
//                    TODO("реализовать URL построитель (с входящими аргументами, id задачи например")
                        attributes["hx-get"] = "/tasks/123/sets/new"
                        attributes["hx-target"] = "#entry-set-new"
                        +"Открыть форму ввода подхода по задаче"
                    }
                    div { id = "entry-set-new" }
                    hr{}

                    button {
                        attributes["hx-get"] = "/workouts/{workoutId}/sets/{setId}/steps/{stepId}/task"
                        attributes["hx-target"] = "#read-set"
                        +"Открыть задание сета"
                    }
                    div { id = "read-set" }
                    hr{}

                    button {
                        attributes["hx-get"] = "/workouts/{workoutId}"
                        attributes["hx-target"] = "#workout-div"
                        +"Открыть (выполненную) тренировку №1"
                        +" которая отображает все задания и все выполненные подходы"
                    }
                    div { id = "workout-div" }
                    hr{}

                }
            }
        }
        // Другие мелкие маршруты
        get("/hello") {
            val name = call.request.queryParameters["name"] ?: "World"
            call.respondHtml { body { +"👋 Привет, $name!" } }
        }
    }
    ApplicationConfig(this).configureRoutes()


}