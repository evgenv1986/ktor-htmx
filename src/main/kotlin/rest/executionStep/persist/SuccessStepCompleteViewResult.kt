package com.example.rest.executionStep.persist

import com.example.rest.executionStep.input.InputExerciseStep
import io.ktor.server.application.ApplicationCall
import io.ktor.server.html.respondHtml
import kotlinx.html.FlowContent
import kotlinx.html.body
import kotlinx.html.div
import kotlinx.html.style

class SuccessStepCompleteViewResult() {
    fun FlowContent.invoke(step: InputExerciseStep) {
        successMessage(step)
//        call.respondHtml {
//            body {
//                successMessage(step)
//            }
//        }
    }
    fun FlowContent.successMessage(step: InputExerciseStep) {
        div {
            style = "color: green; font-weight: bold;"
               +"✅ Сохранено: "
                    +"упражнение ${step.exerciseName} "
                    +"${step.reps} раз "
                    +"по ${step.weight} кг"
        }
    }
}