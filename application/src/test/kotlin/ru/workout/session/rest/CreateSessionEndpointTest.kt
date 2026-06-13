package ru.workout.session.rest

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
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
    "write parameter value into url string"{
        val workoutId = "w1"
        val url = "/workouts/sessions/${workoutId}/preparation"
        url shouldBe "/workouts/sessions/w1/preparation"
    }

})