package rest.workout

import com.example.rest.executionStep.main.module
import com.example.rest.executionStep.main.workout.WorkoutInput
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.testing.testApplication

class AddWorkoutEndpointTest: StringSpec ({
    "should return 201 ok on add a valid workout"{
        testApplication {
            application {
                module()
            }
            val jsonClient = createClient {
                install(io.ktor.client.plugins.contentnegotiation.ContentNegotiation) {
                    json()
                }
            }
            val response = jsonClient.post("/workouts/plannings/add") {
                contentType(ContentType.Application.Json) // Указываем, что шлем JSON
                setBody(WorkoutInput(
                    workoutText = "dsfdsfdsfsdf"
                ))
            }

            response.status shouldBe HttpStatusCode.Companion.OK
            val body = response.bodyAsText() // Получаем HTML как строку
            response.status shouldBe HttpStatusCode.OK
            body shouldContain "Тренировка сохранена"
//            body shouldContain "hx-post=\"/workouts/performances\"" // Проверяем наличие HTMX атрибута
//            body shouldContain "id=\"performance-input-container\""
        }
    }
})