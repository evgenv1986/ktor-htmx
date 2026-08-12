package ru.workout.workout.rest.test

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.testing.testApplication
import ru.workout.application.module
import ru.workout.workout.rest.main.WorkoutStartingRequest

class StartWorkoutEndpointTest: StringSpec({
    "should create workout"{
        testApplication {
            application { module() }
            val jsonClient = createClient {
                install(ContentNegotiation) {
                    json()
                }
            }
            val workoutId = "w1"
            val workout = WorkoutStartingRequest()
            val response = jsonClient.post("/workouts/${workoutId}/starting") {
                contentType(ContentType.Application.Json)
                setBody(workout)
            }
            response.status shouldBe HttpStatusCode.OK
            response.contentType().toString() shouldContain ("text/html")
            val body = response.bodyAsText()
            body shouldContain workoutId

        }
    }
})