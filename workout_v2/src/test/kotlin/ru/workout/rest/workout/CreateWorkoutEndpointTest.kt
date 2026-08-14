package ru.workout.rest.workout

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.types.shouldBeInstanceOf
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.testing.testApplication
import ru.workout.application.module
import ru.workout.application.ru.workout.rest.workout.RoundStartingRequest
import ru.workout.application.ru.workout.rest.workout.SetStartingRequest
import ru.workout.application.ru.workout.rest.workout.StepStartingRequest
import ru.workout.application.ru.workout.rest.workout.WorkoutCreationRequest
import ru.workout.application.ru.workout.usecase.workout.WorkoutDto

class CreateWorkoutEndpointTest: StringSpec({
    "should create workout from request"{
        testApplication {
            application { module() }
            val jsonClient = createClient {
                install(ContentNegotiation) {
                    json()
                }
            }
            val workoutId = "w1"
            val request: WorkoutCreationRequest = workoutRequest()
            val response = jsonClient.post("/workouts/${workoutId}/creation") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }
            response.status shouldBe HttpStatusCode.OK
            response.contentType().toString() shouldContain ("text/html")
            val body = response.bodyAsText()
            body shouldContain workoutId

            request.sets.first().rounds.first().steps.first().exercise shouldBe "Подъем с переворотом"
            request.sets.first().rounds.first().steps.first().reps shouldBe 1
        }
    }
    "should create workoutDto from workout-request"{
        val request: WorkoutCreationRequest = workoutRequest()
        val workoutDto: WorkoutDto = request.toWorkoutDto()
        workoutDto.shouldBeInstanceOf<WorkoutDto>()
        workoutDto.sets.count() shouldBeEqual (request.sets.count())
    }
})


fun workoutRequest(): WorkoutCreationRequest =
    WorkoutCreationRequest(
        sets = listOf(
            SetStartingRequest(
                rounds = listOf(
                    RoundStartingRequest(
                        id = "r1",
                        steps = listOf(
                            StepStartingRequest(exercise = "Подъем с переворотом", reps = 1),
                            StepStartingRequest(exercise = "Отжимания в упоре", reps = 10),
                            StepStartingRequest(exercise = "Подтягивания", reps = 5),
                        )
                    ),
                    RoundStartingRequest(
                        id = "r2",
                        steps = listOf(
                            StepStartingRequest(exercise = "Подъем с переворотом", reps = 1),
                            StepStartingRequest(exercise = "Отжимания в упоре", reps = 10),
                            StepStartingRequest(exercise = "Подтягивания", reps = 5),
                        )
                    ),
                    RoundStartingRequest(
                        id = "r3",
                        steps = listOf(
                            StepStartingRequest(exercise = "Подъем с переворотом", reps = 1),
                            StepStartingRequest(exercise = "Отжимания в упоре", reps = 10),
                            StepStartingRequest(exercise = "Подтягивания", reps = 5),
                        )
                    ),
                    RoundStartingRequest(
                        id = "r4",
                        steps = listOf(
                            StepStartingRequest(exercise = "Подъем с переворотом", reps = 1),
                            StepStartingRequest(exercise = "Отжимания в упоре", reps = 10),
                            StepStartingRequest(exercise = "Подтягивания", reps = 5),
                        )
                    ),
                )
            ),
            SetStartingRequest(
                rounds = listOf(
                    RoundStartingRequest(
                        id = "r1",
                        steps = listOf(
                            StepStartingRequest(exercise = "маятниковый австралийские подтягивания", reps = 15 - 20),
                            StepStartingRequest(exercise = "махи на плечи с резиной", reps = 30),
                        )
                    ),
                    RoundStartingRequest(
                        id = "r1",
                        steps = listOf(
                            StepStartingRequest(exercise = "маятниковый австралийские подтягивания", reps = 15 - 20),
                            StepStartingRequest(exercise = "махи на плечи с резиной", reps = 30),
                        )
                    ),
                    RoundStartingRequest(
                        id = "r1",
                        steps = listOf(
                            StepStartingRequest(exercise = "маятниковый австралийские подтягивания", reps = 15 - 20),
                            StepStartingRequest(exercise = "махи на плечи с резиной", reps = 30),
                        )
                    )
                )
            )
        ),
    )


val text = """
        1 подъем переворотом + 
        10 отжиманий в упоре + 
        5 подтягиваний. 
        6-8 подходов.
        
        Маятниковые австралийские подтягивания 15-20 повторов + махи на плечи с резиной 30 повторов. 4 подхода. 
        Болгарские приседания по 15 повторов на каждую ногу + ласточки на каждую ногу по 10 + ягодичный мост по 1 ноге в динамике на каждую. 3 подхода.
        Стульчик 45 секунд + приседания с низу до середины 10 повторов с середины до верха 10 и 10 обычных. 3 подхода.
        Джеки тотал 200. После каждой остановки подъемы ног в упоре на брусьях   
    """.trimIndent()