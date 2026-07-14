package ru.workout.session.rest

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.testing.testApplication
import ru.workout.application.module
import ru.workout.rest.COMPLETION_REPS_NEW

class CompletionRepsTest : StringSpec({

    "completion reps should be invoked" {
        testApplication {
            application {
                module()
            }

            val jsonClient = createClient {
                install(ContentNegotiation) {
                    json()
                }
            }

            val exerciseName = "подтягивания"
            val reps = 30

            val completionRepsView = jsonClient.get (COMPLETION_REPS_NEW)
            val html = completionRepsView.bodyAsText()
            html shouldContain exerciseName
            html shouldContain reps.toString()
            val workoutId = 123
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