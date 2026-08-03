package ru.workout.session.rest.v2

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import ru.workout.application.module
import io.ktor.server.testing.testApplication

class RootTest: StringSpec({
    "should return 200 OK on root url" {
        testApplication {
            application { module() }
            val response = client.get("/workouts/v2")
            response.status shouldBe HttpStatusCode.OK
            response.bodyAsText() shouldContain "Тренировки"
        }
    }
})