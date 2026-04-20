package com.example.rest.executionStep.persist

import com.example.rest.executionStep.input.InputExerciseStep
import io.ktor.server.application.ApplicationCall
import io.ktor.server.html.respondHtml
import io.ktor.server.request.receive
import kotlinx.html.body

class StepCompleteEndpoint(private val successStepCompleteViewResult: SuccessStepCompleteViewResult) {
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