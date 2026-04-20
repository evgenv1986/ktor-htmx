package com.example

import com.example.rest.executionStep.persist.StepCompleteEndpoint
import com.example.rest.executionStep.step.InputStepRoute
import com.example.rest.executionStep.persist.StepCompleteRoute
import com.example.rest.executionStep.persist.SuccessStepCompleteViewResult
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

fun main() {
    val port = System.getenv("PORT")?.toInt() ?: 8080
    embeddedServer(Netty, port = port) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                coerceInputValues = true
            })
        }
        // Создаем экземпляр нашего вынесенного класса
        val registrationHandler = UserRegistrationHandler()
//        val exerciseStepView = InputExerciseStepPerformView()

        routing {
            var counter = 0
            // Регистрация маршрутов из внешнего класса
            registrationHandler.registerRoutes(this)
            // маршрут формы ввода выполнения сета упражнения
//            CreateStepRoute(this).register()

            InputStepRoute(this).register()
            StepCompleteRoute(
                this,
                StepCompleteEndpoint(
                    SuccessStepCompleteViewResult()
                )).register()

            get("/") {
                call.respondHtml(HttpStatusCode.OK) {
                    lang = "ru"
                    head {
                        title("Ktor + HTMX")
                        meta { charset = "UTF-8" }
                        script { src = "https://unpkg.com/htmx.org@1.9.12" }
                        script { src = "https://unpkg.com/htmx.org/dist/ext/json-enc.js" }
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
                    }
                }
            }
            // Другие мелкие маршруты
            get("/hello") {
                val name = call.request.queryParameters["name"] ?: "World"
                call.respondHtml { body { +"👋 Привет, $name!" } }
            }
        }
    }.start(wait = true)
}


