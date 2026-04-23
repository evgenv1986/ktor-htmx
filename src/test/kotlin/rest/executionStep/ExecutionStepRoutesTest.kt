package rest.executionStep

import com.example.rest.executionStep.main.input.InputExerciseStep
import com.example.rest.executionStep.main.module
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.testApplication
import io.ktor.serialization.kotlinx.json.json

class ExecutionStepRoutesTest : StringSpec({

    "маршрут должен отдавать 200 OK" {
        testApplication {
            application {
                module()
            }

            // Обращаемся через 'client'
            val response = client.get("/workouts/performances/123")

            response.status shouldBe HttpStatusCode.Companion.OK

            val body = response.bodyAsText() // Получаем HTML как строку

            // Проверки Kotest
            body shouldContain "Задание : Подтягивания"
            body shouldContain "вес 12.1 кг"
            body shouldContain "hx-post=\"/workouts/performances\"" // Проверяем наличие HTMX атрибута
            body shouldContain "id=\"performance-input-container\""
        }
    }

    "ответ должен содержать текст Задание" {
        testApplication {
            application {
                module()
            }

//            val response = client.get("/workouts/performances/123")

            val jsonClient = createClient {
                install(io.ktor.client.plugins.contentnegotiation.ContentNegotiation) {
                    json()
                }
            }
            val response = jsonClient.get("/workouts/performances/123")
            val exerciseCompleted = response.body<InputExerciseStep>()
            exerciseCompleted.exerciseName shouldBe "Подтягивания"
            exerciseCompleted.weight shouldBe 12.1

            // Проверка содержимого (требует импорта client.statement.*)
            val text = response.bodyAsText()
            text.contains("Задание") shouldBe true
        }
    }
})