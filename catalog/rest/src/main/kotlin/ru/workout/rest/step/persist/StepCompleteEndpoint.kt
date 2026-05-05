package ru.workout.rest.step.persist

import io.ktor.server.application.ApplicationCall
import io.ktor.server.html.respondHtml
import io.ktor.server.request.receive
import kotlinx.html.body
import ru.workout.rest.step.input.InputExerciseStep

class StepCompleteEndpoint(
    private val successStepCompleteViewResult: SuccessStepCompleteViewResult) {
    // Логика сохранения
    suspend fun handle(call: ApplicationCall) {
        val step = call.receive<InputExerciseStep>()
        call.respondHtml {
            body {
                with (successStepCompleteViewResult){
                    invoke(step)
                }
            }
        }
    }
}