package ru.workout.session.rest

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.nulls.shouldNotBeNull
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
import org.jsoup.Jsoup
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




    "handle post set for task"{
        testApplication {
            application { module() }
            val taskId = 123
            val response = client.post("/tasks/123/sets") {
                contentType(ContentType.Application.FormUrlEncoded)
                setBody("reps=30")
            }
            response.status shouldBe HttpStatusCode.OK

            response.contentType().toString() shouldContain ("text/html")
            val doc = Jsoup.parse(response.bodyAsText())
            val bodyText = doc.body().text()

            doc.selectFirst("p, .progress-info")
                ?: bodyText.contains("30")
        }
    }
})