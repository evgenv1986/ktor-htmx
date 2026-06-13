package ru.workout.session.rest

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.testing.testApplication
import ru.workout.application.module

class BeginWorkoutEndpointTest : StringSpec({

    "begin prepared workout" {
        testApplication {
            // Настройка приложения
            application {
                module()
            }

            // Настройка клиента
            val jsonClient = createClient {
                install(ContentNegotiation) {
                    json()
                }
            }

            val workoutId = "w1"

            // Выполнение запроса
            val response = jsonClient.put("/workouts/sessions/$workoutId/beginning") {
                contentType(ContentType.Application.Json)
                setBody(workoutId)
            }

            // Проверка (Assertions)
            // Здесь мы используем shouldBe из Kotest
            response.status shouldBe HttpStatusCode.Created
            response.status.value shouldBe 201
        }
    }
})