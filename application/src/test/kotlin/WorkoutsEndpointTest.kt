import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.testing.testApplication
import ru.workout.rest.workout.WorkoutInput
import ru.workout.rest.workout.WorkoutsView
import workout.application.workout.app.module

class WorkoutsEndpointTest: StringSpec({
    "should return workouts text with status 200"{
        val expected = WorkoutsView(listOf("Подтягивания"))
        val workouts = expected
        testApplication {
            application {
                module()
            }
            val jsonClient = createClient {
                install(ContentNegotiation) {
                    json()
                }
            }
            val response = jsonClient.post("/workouts") {
            }
            response.status shouldBe HttpStatusCode.Companion.OK
            val body = response.bodyAsText() // Получаем HTML как строку
            body shouldContain expected.exercises.toString()
        }
    }
})