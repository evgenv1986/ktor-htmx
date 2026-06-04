package ru.workout.session.rest

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.testing.testApplication
import ru.workout.application.module

class CreateSessionEndpointTest: StringSpec({
    "Атлет хочет создать тренировочную сессию на основе плана из каталога." +
    "An athlete wants to create a training session based on a plan from the catalog."
    {
        testApplication {
            application {
                module()
            }
            val jsonClient = createClient {
                install(ContentNegotiation) {
                    json()
                }
            }
            val planId = 123
            val response = jsonClient.post("/workouts/sessions/preparation") {
                contentType(ContentType.Application.Json) // Указываем, что шлем JSON
                setBody(planId)
            }

            response.status shouldBe HttpStatusCode.Created
        }
    }
})