package ru.workout.application

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.html.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.call
import io.ktor.server.routing.get
import kotlinx.html.*
import kotlinx.serialization.json.Json
import kotlin.collections.set

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

    routing {
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

                }
            }
        }

        get ("/steps/{stepId}/completion") {
            val stepId = call.parameters["stepId"]!!
            call.respondHtml {
                body{
                    script { src = "https://unpkg.com/htmx.org@1.9.10" }
                    div {
                        div {
                            id = "step-completion-id"
                            loadStepTask(call)()
//                            id = "step-task-id"
//                            attributes["hx-get"] = "/steps/${stepId}/task"
//                            attributes["hx-trigger"] = "load"
//                            attributes["hx-target"] = "#step-task-id"
//                            attributes["hx-swap"] = "innerHTML"
                        }
                        hr{}
                        div {
                            id = "step-progress-id"
                            attributes["hx-get"] = "/steps/${stepId}/progress"
                            attributes["hx-trigger"] = "load"
                            attributes["hx-target"] = "#step-progress-id"
                            attributes["hx-swap"] = "innerHTML"
                            p { +"- с прогрессом( статистика выполнения) " } }
                        hr{}
                        div {
                            id = "completion-new-id"
                            p {+"форма ввода повторений"}
                        }
                    }
                }
            }
        }
        stepTaskEndpoint()
        get ("/steps/{stepId}/progress"){
            val stepId = call.parameters["stepId"]!!
            call.respondHtml {
                body {
                    div {
                        id = "step-progress-id"
                        hr {}
                        h4 { +"📊 Прогресс шага по id = $stepId" }
                        p { +"Выполнено: 0 из 0 подходов" }
                        progress {
                            max = stepId.toInt().toString()
                            value = "0"
                            style = "width: 100%;"
                        }
                    }
                }
            }
        }
        get ("/workouts/{workoutId}" +
                "/sets/{setId}" +
                "/rounds/{roundId}" +
                "/steps" +
                "/{stepId}" +
                "/completion" +
                "/new")
        {
            call.respondHtml { body{
                div {
                    id = "completion-new-id"
                    p { +"- с элементами для ввода текущих показателей " }
                    form {
                        attributes["hx-post"] = "/workouts/" +
                                "{workoutId}" +
                                "/sets/{setId}" +
                                "/rounds/{roundId}" +
                                "/steps" +
                                "/{stepId}" +
                                "/completion"
                        attributes["hx-target"] = "#completion-new-id"
                        attributes["hx-swap"] = "outerHTML"

                        input(type = InputType.number, name = "reps") {
                            placeholder = "Количество повторений"
                            required = true
                            min = "1"
                            width = "1"
                        }
                        button(type = ButtonType.submit) { +"Выполнил" }
                    }
                }
            }}
        }
    }
}
fun loadStepTask(call: ApplicationCall): DIV.() -> Unit = {
    val stepId = call.parameters["stepId"]!!
    div {
            id = "step-task-id"
            attributes["hx-get"] = "/steps/${stepId}/task"
            attributes["hx-trigger"] = "load"
            attributes["hx-target"] = "#step-task-id"
            attributes["hx-swap"] = "innerHTML"
    }
}

fun Route.stepTaskEndpoint(){
    get ("/steps/{stepId}/task") {
        StepTaskEndpoint().invoke(call)
    }
}
class StepTaskEndpoint{
    suspend fun invoke(call: ApplicationCall){
        val stepId = call.parameters["stepId"]!!
        call.respondHtml {
            body {
                div {
                    stepTaskContent(call)()
                }
            }
        }
    }
    fun stepTaskContent(call: ApplicationCall): DIV.() -> Unit = {
        val stepId = call.parameters["stepId"]!!
        div {
            p {+"форма с заданием для шага stepsId = ${stepId}"}
        }
    }
}