package ru.workout.application.rest.step.persist

import kotlinx.html.FlowContent
import kotlinx.html.div
import kotlinx.html.style
import ru.workout.application.rest.step.input.InputExerciseStep

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