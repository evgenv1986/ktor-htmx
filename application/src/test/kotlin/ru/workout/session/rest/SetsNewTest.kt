package ru.workout.session.rest

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.testApplication
import org.jsoup.Jsoup
import ru.workout.application.module
import ru.workout.session.domain.additional.AdditionalTask
import kotlin.test.assertTrue

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
            val doc = Jsoup.parse(response.bodyAsText())
            val exerciseElement = doc.selectFirst("h1, h2, .exercise-name")
                ?: doc.body()
            exerciseElement.text().shouldContain("Подтягивания")
        }
    }
    "form exist element"{
        testApplication {
            application { module() }
            val taskId = 123
            val response = client.get("/tasks/$taskId/sets/new")
                .apply{
                    status shouldBe HttpStatusCode.OK
                }
            val doc = Jsoup.parse(response.bodyAsText())
            val exerciseElement = doc.selectFirst("h1, h2, .exercise-name")
                ?: doc.body()
            val form = doc.selectFirst("form")
            form.shouldNotBeNull()
        }
    }
    "form submit url should be /tasks/{taskId}/sets"{
        testApplication {
            application { module() }
            val taskId = 123
            val response = client.get("/tasks/$taskId/sets/new")
                .apply{
                    status shouldBe HttpStatusCode.OK
                }
            val doc = Jsoup.parse(response.bodyAsText())
            val exerciseElement = doc.selectFirst("h1, h2, .exercise-name")
                ?: doc.body()
            val form = doc.selectFirst("form")
            val formAction = form.attr("hx-post")
                formAction.shouldContain("/tasks/$taskId/sets")

        }
    }
})