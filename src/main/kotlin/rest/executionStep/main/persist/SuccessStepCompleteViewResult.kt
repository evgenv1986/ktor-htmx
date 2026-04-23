package com.example.rest.executionStep.main.persist

import com.example.rest.executionStep.main.input.InputExerciseStep
import kotlinx.html.FlowContent
import kotlinx.html.div
import kotlinx.html.style

class SuccessStepCompleteViewResult() {
    fun FlowContent.invoke(step: InputExerciseStep) {
        successMessage(step)
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