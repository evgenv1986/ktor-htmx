package com.example

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.html.*
import io.ktor.server.netty.*
import io.ktor.server.routing.*
import kotlinx.html.*

fun main() {
    val port = System.getenv("PORT")?.toInt() ?: 8080

    embeddedServer(Netty, port = port) {
        routing {
            var counter = 0

            get("/") {
                call.respondHtml(HttpStatusCode.OK) {
                    lang = "ru"
                    head {
                        title("Hello World")
                        meta { charset = "UTF-8" }
                        script { src = "https://unpkg.com/htmx.org@1.9.12" }
                        style {
                            unsafe {
                                +"""
                                body { font-family: sans-serif; max-width: 400px; margin: 60px auto; padding: 0 16px; }
                                input { padding: 8px; border: 1px solid #ccc; border-radius: 4px; margin-right: 8px; width: 150px; }
                                button { padding: 8px 16px; background: #3b82f6; color: white; border: none; border-radius: 4px; cursor: pointer; }
                                button:hover { background: #2563eb; }
                                #result { margin-top: 16px; font-size: 1.2rem; }
                                #counter { margin-top: 8px; }
                                hr { margin: 24px 0; border: none; border-top: 1px solid #e5e7eb; }
                                .form-row { display: flex; gap: 8px; align-items: center; }
                                """.trimIndent()
                            }
                        }
                    }
                    body {
                        h2 { +"Ktor + HTMX V2" }

                        // --- Секция 1: Приветствие ---
                        div {
                            // Форма для приветствия
                            form {
                                attributes["hx-get"] = "/hello"
                                attributes["hx-target"] = "#result"
                                attributes["hx-swap"] = "innerHTML"

                                div("form-row") {
                                    input(type = InputType.text) {
                                        name = "name"
                                        placeholder = "Введите имя"
                                        value = "World"
                                    }
                                    button {
                                        type = ButtonType.submit
                                        +"Сказать привет"
                                    }
                                }
                            }
                            div { id = "result" }
                        }

                        hr {}

                        // --- Секция 2: Счётчик ---
                        div {
                            button {
                                attributes["hx-post"] = "/click"
                                attributes["hx-target"] = "#counter"
                                attributes["hx-swap"] = "innerHTML"
                                +"Нажми меня"
                            }
                            div { id = "counter" }
                        }
                    }
                }
            }

            get("/hello") {
                val name = call.request.queryParameters["name"] ?: "World"
                call.respondHtml {
                    body { +"👋 Hello, $name!" }
                }
            }

            post("/click") {
                counter++
                call.respondHtml {
                    body { +"Нажато: $counter раз(а)" }
                }
            }
        }
    }.start(wait = true)
}