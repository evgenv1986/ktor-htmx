package com.example

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.html.*
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val lastName: String,
    val firstName: String,
    val middleName: String?,
    val age: Int
)

class UserRegistrationHandler {

    // 1. Функция для отрисовки формы (UI)
    // Используем расширение FlowContent, чтобы функцию можно было вызвать внутри любого div/body
    fun FlowContent.renderRegistrationForm() {
        div {
            h3 { +"3. Регистрация пользователя (отправка JSON)" }
            form {
                attributes["hx-ext"] = "json-enc"
                attributes["hx-post"] = "/register"
                attributes["hx-target"] = "#registration-result"
                attributes["hx-swap"] = "outerHTML"

                input { name = "lastName"; placeholder = "Фамилия"; required = true }
                input { name = "firstName"; placeholder = "Имя"; required = true }
                input { name = "middleName"; placeholder = "Отчество (необязательно)" }
                input(type = InputType.number) {
                    name = "age"
                    placeholder = "Возраст"
                    min = "1"
                    max = "120"
                    required = true
                }
                button { type = ButtonType.submit; +"Зарегистрировать" }
            }
        }
    }

    // 2. Функция для регистрации маршрутов (Логика)
    fun registerRoutes(routing: Routing) {
        routing.post("/register") {
            try {
                val user = call.receive<User>()
                validate(user)

                println("УСПЕШНО зарегистрирован: $user")

                call.respondHtml {
                    body { renderSuccessMessage(user) }
                }

            } catch (e: Exception) {
                println("Ошибка: ${e.message}")
                call.respondHtml(HttpStatusCode.BadRequest) {
                    body { renderErrorMessage(e.message ?: "Ошибка данных") }
                }
            }
        }
    }

    // Вспомогательная функция валидации
    private fun validate(user: User) {
        when {
            user.lastName.isBlank() -> throw IllegalArgumentException("Фамилия обязательна")
            user.firstName.isBlank() -> throw IllegalArgumentException("Имя обязательно")
            user.lastName.length < 2 -> throw IllegalArgumentException("Фамилия слишком короткая")
        }
    }

    // Фрагмент успеха
    private fun FlowContent.renderSuccessMessage(user: User) {
        div {
            id = "registration-result"
            style = "background:#ecfdf5; border:2px solid #22c55e; padding:16px; border-radius:8px; color:#166534;"
            h3 { +"Успешная регистрация!" }
            p { +"Добро пожаловать, ${user.lastName} ${user.firstName}!" }
        }
    }

    // Фрагмент ошибки
    private fun FlowContent.renderErrorMessage(message: String) {
        div {
            id = "registration-result"
            style = "background:#fef2f2; border:2px solid #ef4444; padding:16px; border-radius:8px; color:#991b1b;"
            h3 { +"Ошибка" }
            p { +message }
        }
    }
}