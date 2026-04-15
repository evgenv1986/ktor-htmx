package com.example

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.html.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.html.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json // Дополнительный импорт

@Serializable
data class User(val lastName: String, val firstName: String, val middleName: String?)

fun main() {
    val port = System.getenv("PORT")?.toInt() ?: 8080

    embeddedServer(Netty, port = port) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                coerceInputValues = true   // ← вот эта строка превращает "" → null для String?
                encodeDefaults = true
            })
        }

        routing {
            var counter = 0

            get("/") {
                call.respondHtml(HttpStatusCode.OK) {
                    // ... (весь ваш HTML-код остается без изменений)
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
                                button:hover { background: #2563eb; }
                                hr { margin: 32px 0; border: none; border-top: 1px solid #e5e7eb; }
                                h2, h3 { margin-bottom: 16px; }
                                #result, #counter, #registration-result { margin-top: 10px; }
                                .form-row { display: flex; gap: 8px; align-items: center; }
                                """.trimIndent()
                            }
                        }
                    }
                    body {
                        h2 { +"Ktor + HTMX" }

                        div {
                            h3 { +"1. Приветствие" }
                            form {
                                attributes["hx-get"] = "/hello"; attributes["hx-target"] = "#result"; attributes["hx-swap"] = "innerHTML"
                                div("form-row") {
                                    input(type = InputType.text) { name = "name"; value = "World" }
                                    button { type = ButtonType.submit; +"Сказать привет" }
                                }
                            }
                            div { id = "result" }
                        }
                        hr {}
                        div {
                            h3 { +"2. Счётчик" }
                            button {
                                attributes["hx-post"] = "/click"; attributes["hx-target"] = "#counter"; attributes["hx-swap"] = "innerHTML"
                                +"Нажми меня"
                            }
                            div { id = "counter" }
                        }
                        hr {}
                        div {
                            h3 { +"3. Регистрация пользователя (отправка JSON)" }
                            form {
                                attributes["hx-ext"] = "json-enc";
                                attributes["hx-post"] = "/register";
                                attributes["hx-target"] = "#registration-result";
                                attributes["hx-swap"] = "outerHTML"
                                input { name = "lastName"; placeholder = "Фамилия"; required = true }
                                input { name = "firstName"; placeholder = "Имя"; required = true }
                                input { name = "middleName"; placeholder = "Отчество (необязательно)" }
                                button { type = ButtonType.submit; +"Зарегистрировать" }
                            }
                            div { id = "registration-result" }
                        }
                    }
                }
            }

            get("/hello") {
                val name = call.request.queryParameters["name"] ?: "World"
                call.respondHtml { body { +"👋 Hello, $name!" } }
            }

            post("/click") {
                counter++
                call.respondHtml { body { +"Нажато: $counter раз(а)" } }
            }

            // === ИСПРАВЛЕННЫЙ ЭНДПОИНТ /register ===
            post("/register") {
                try {
                    val user = call.receive<User>()

                    // Ручная валидация (человеческая)
                    when {
                        user.lastName.isBlank() -> throw IllegalArgumentException("Фамилия обязательна")
                        user.firstName.isBlank() -> throw IllegalArgumentException("Имя обязательно")
                        user.lastName.length < 2 -> throw IllegalArgumentException("Фамилия слишком короткая")
                        user.firstName.length < 2 -> throw IllegalArgumentException("Имя слишком короткое")
                        user.middleName?.let { it.length < 2 && it.isNotBlank() } == true ->
                            throw IllegalArgumentException("Отчество должно быть полным или пустым")
                    }

                    println("УСПЕШНО зарегистрирован: $user")

                    call.respondHtml {
                        body {
                            div {
                                id = "registration-result"
                                style = "background:#ecfdf5; border:2px solid #22c55e; padding:16px; border-radius:8px; color:#166534;"
                                h3 { +"Успешная регистрация!" }
                                p {
                                    +"Добро пожаловать, "
                                    strong { +"${user.lastName} ${user.firstName} ${user.middleName?.let { "$it " } ?: ""}" }
                                    +"!"
                                }
                            }
                        }
                    }

                } catch (e: Exception) {
                    println("Ошибка: ${e.message}")

                    call.respondHtml(HttpStatusCode.BadRequest) {
                        body {
                            div {
                                id = "registration-result"
                                style = "background:#fef2f2; border:2px solid #ef4444; padding:16px; border-radius:8px; color:#991b1b;"
                                h3 { "Ошибка" }
                                p { +(e.message ?: "Неверные данные") }
                                p {
                                    style = "font-size:0.9rem; margin-top:8px; opacity:0.8;"
                                    +"Попробуйте ещё раз"
                                }
                            }
                        }
                    }
                }
            }
        }
    }.start(wait = true)
}