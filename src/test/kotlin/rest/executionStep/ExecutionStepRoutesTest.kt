package rest.executionStep

import com.example.rest.executionStep.main.input.InputExerciseStep
import com.example.rest.executionStep.main.module
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.server.testing.testApplication
import io.ktor.serialization.kotlinx.json.json

class ExecutionStepRoutesTest : StringSpec({
    "should return 200 OK on request active step"{
        testApplication {
            application {
                module()
            }
            val response = client
                .get("/workouts/performances/123")
        }
    }
    "the route should return 200 OK" {
        testApplication {
            application {
                module()
            }
            // Обращаемся через 'client'
            val response = client.get("/workouts/performances/123")
            response.status shouldBe HttpStatusCode.Companion.OK
            val body = response.bodyAsText() // Получаем HTML как строку
            // Проверки Kotest
            response.status shouldBe HttpStatusCode.OK
            body shouldContain "Задание : Подтягивания"
            body shouldContain "вес 12.1 кг"
            body shouldContain "hx-post=\"/workouts/performances\"" // Проверяем наличие HTMX атрибута
            body shouldContain "id=\"performance-input-container\""
        }
    }

    "A valid Answer must contain the text of the task" {
        testApplication {
            application {
                module()
            }
            val jsonClient = createClient {
                install(io.ktor.client.plugins.contentnegotiation.ContentNegotiation) {
                    json()
                }
            }
            val stepData = InputExerciseStep(
                exerciseName = "Приседания",
                weight = 10.0,
                15
            )
            val response = jsonClient.post("/workouts/performances") {
                contentType(ContentType.Application.Json) // Указываем, что шлем JSON
                setBody(stepData) // Сериализация произойдет автоматически
            }
            response.status shouldBe HttpStatusCode.OK

            val htmlResult = response.bodyAsText()
            // Мы проверяем, что данные, которые мы послали в JSON,
            // сервер успешно получил и "вставил" в HTML-ответ.
            htmlResult shouldContain "✅ Сохранено"
            htmlResult shouldContain "Приседания"
            htmlResult shouldContain "10.0"
        }
    }
    "POST request with broken JSON should return 400" {
        testApplication {
            application { module() }
            val response = client.post("/workouts/performances") {
                contentType(ContentType.Application.Json)
                setBody("""{"bad_field": "unknown"}""") // Шлем ерунду
            }
            response.status shouldBe HttpStatusCode.BadRequest
            response.bodyAsText() shouldContain "Ошибка"
        }
    }
})