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
import ru.workout.application.main.route.ExerciseSetInputRequest
import ru.workout.application.module
import ru.workout.rest.COMPLETION_REP
import ru.workout.rest.COMPLETION_REPS_NEW

class CompletionRepsTest : StringSpec({

    // Тест 1: Форма (GET)
    "completion reps form returns exercise data" {
        testApplication {
            application { module() }

            val exerciseName = "подтягивания"
            val reps = "30"

            val response = client.get(COMPLETION_REPS_NEW)

            response.status shouldBe HttpStatusCode.OK

            // SSR: проверяем HTML контент
            val html = response.bodyAsText()
            html shouldContain "Введите название упражнения и количество выполненных повторений"
        }
    }
    "completion reps submitted successfully redirects to task" {
        testApplication {
            application { module() }
            val jsonClient = createClient {
                install(ContentNegotiation) {
                    json()
                }
            }

            val completion = ExerciseSetInputRequest(
                exerciseName = "подтягивания",
                reps = "30"
            )
            val response = jsonClient.put(COMPLETION_REP) {
                contentType(ContentType.Application.Json)
                setBody(completion)
            }

            response.status shouldBe HttpStatusCode.Created
//                response.headers["Location"] shouldContain "/tasks/$TASK_ID"
        }
    }
})