//package ru.workout.rest
//
//import io.kotest.core.spec.style.StringSpec
//import io.kotest.matchers.shouldBe
//import io.kotest.matchers.string.shouldContain
//import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
//import io.ktor.client.request.post
//import io.ktor.client.request.setBody
//import io.ktor.client.statement.bodyAsText
//import io.ktor.http.ContentType
//import io.ktor.http.HttpStatusCode
//import io.ktor.http.contentType
//import io.ktor.serialization.kotlinx.json.json
//import io.ktor.server.testing.testApplication
//import ru.workout.rest.workout.WorkoutInput
//import workout.application.workout.app.module
//
//class AddWorkoutEndpointTest: StringSpec({
//    "should return 201 ok on add a valid workout"{
//        testApplication {
//            application {
//                module()
//            }
//            val jsonClient = createClient {
//                install(ContentNegotiation) {
//                    json()
//                }
//            }
//            val workoutText = """
//                    1. Подтягивания с 10 кг на 1 повторов. 3 минуты работы. Максимальное число повторов в ТОТАЛ. 
//                    2. Отжимания на брусьях с 16 кг на 2 повторов. 3 минуты работы. Максимальное число повторов в ТОТАЛ.
//                """.trimIndent()
//            val response = jsonClient.post("/workouts/plannings/add") {
//                contentType(ContentType.Application.Json) // Указываем, что шлем JSON
//                setBody(WorkoutInput(workoutText))
//            }
//
//            response.status shouldBe HttpStatusCode.Companion.OK
//            val body = response.bodyAsText() // Получаем HTML как строку
//            body shouldContain "Тренировка сохранена"
////            body shouldContain workoutText
////            body shouldContain "hx-post=\"/workouts/performances\"" // Проверяем наличие HTMX атрибута
////            body shouldContain "id=\"performance-input-container\""
//        }
//    }
//    "should return status code 422 with error: exercises: Тренировка должна быть заполнена упражнениями"{
//        testApplication {
//            application {
//                module()
//            }
//            val jsonClient = createClient {
//                install(ContentNegotiation) {
//                    json()
//                }
//            }
//            val workoutText = ""
//            val response = jsonClient.post("/workouts/plannings/add") {
//                contentType(ContentType.Application.Json)
//                setBody(WorkoutInput(workoutText))
//            }
//
//            response.status shouldBe HttpStatusCode.Companion.UnprocessableEntity
//            val body = response.bodyAsText()
//            body shouldContain "exercises: Тренировка должна быть заполнена упражнениями"
//        }
//    }
//})