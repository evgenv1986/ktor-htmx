package ru.workout.session.rest

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.testing.ApplicationTestBuilder
import io.ktor.server.testing.testApplication
import ru.workout.application.module
import ru.workout.catalog.rest.CompleteStepRequest
import ru.workout.rest.COMPLETE_STEP

private suspend fun ApplicationTestBuilder.putJson(
    url: String,
    data: Any
): HttpResponse {
    val jsonClient = createClient {
        install(ContentNegotiation) {
            json()
        }
    }
    return jsonClient.put(url) {
        contentType(ContentType.Application.Json)
        setBody(data)
    }
}

class CompleteStepEndpointTest: StringSpec({
    "compete step url"{
        val stepId = "stepId1"
        val expect = "/steps/{stepId}/completion"
        COMPLETE_STEP shouldBe expect
    }
    "complete step of workout session"{
        val stepId = "stepId1"
        val step = CompleteStepRequest(actualReps = 30)
        testApplication {
            application { module() }

            val response = putJson("/steps/$stepId/completion", step)
            response.status shouldBe HttpStatusCode.Created
            response.status.value shouldBe 201

            val responseBody = response.body<Map<String, String>>()
            val actualStepId = responseBody["step completed successfully"]
            actualStepId shouldBe stepId
        }
    }
})


