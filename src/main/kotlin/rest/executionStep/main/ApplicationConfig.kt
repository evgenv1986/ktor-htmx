package com.example.rest.executionStep.main

import com.example.rest.executionStep.main.input.InputStepEndpoint
import com.example.rest.executionStep.main.input.InputStepPerformView
import com.example.rest.executionStep.main.persist.StepCompleteEndpoint
import com.example.rest.executionStep.main.persist.StepCompleteRoute
import com.example.rest.executionStep.main.persist.SuccessStepCompleteViewResult
import com.example.rest.executionStep.main.step.InputStepRoute
import io.ktor.server.application.*
import io.ktor.server.routing.*

class ApplicationConfig(val app: Application) {
    fun configureRoutes(){
        app.routing {
            InputStepRoute(
                this,
                InputStepEndpoint(
                    InputStepPerformView()
                ))
                .register()
            StepCompleteRoute(
                this,
                StepCompleteEndpoint(
                    SuccessStepCompleteViewResult()
                ))
                .register()
        }
    }
}