package ru.workout.session.rest

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.testApplication
import ru.workout.application.module

class SetsNewTest: StringSpec({
    "route for entry form for adding sets for task should be existed"{
        testApplication {
            application { module() }
            val taskId = 123
            val response = client.get("/tasks/$taskId/sets/new")
                .apply{
                    status shouldBe HttpStatusCode.OK
                }
        }
    }
    "entry form for adding sets for task should contain name of the exercise"{
        testApplication {
            application { module() }
            val taskId = 123
            val response = client.get("/tasks/$taskId/sets/new")
                .apply{
                    status shouldBe HttpStatusCode.OK
                }
        }
    }
})