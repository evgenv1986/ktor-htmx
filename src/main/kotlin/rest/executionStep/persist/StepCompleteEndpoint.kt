package com.example.rest.executionStep.persist

import com.example.rest.executionStep.input.InputExerciseStep
import io.ktor.server.application.ApplicationCall
import io.ktor.server.request.receive

class StepCompleteEndpoint {
    // Логика сохранения
    suspend fun handle(call: ApplicationCall) {
        val step = call.receive<InputExerciseStep>()
        with (SuccessStepCompleteViewResult(call)){
            invoke(step)
        }
    }
}