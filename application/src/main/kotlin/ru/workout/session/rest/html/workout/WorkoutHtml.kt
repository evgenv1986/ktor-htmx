package ru.workout.session.rest.html.workout

import io.ktor.server.application.ApplicationCall
import kotlinx.html.FlowContent
import kotlinx.html.br
import kotlinx.html.div
import kotlinx.html.hr
import kotlinx.html.id
import kotlinx.html.p
import kotlinx.html.span
import kotlinx.html.style

class WorkoutHtml(val call: ApplicationCall) {
    fun render(content: FlowContent, workout: WorkoutResponse) {
        content.div {
            id = "workoutContainer"
            p {
                style = "white-space: pre-line; line-height: 1.6"
                +"""
                    Описание:
                    1. Махи на плечи с резиной 30 повторов 15-25 отжиманий на брусьях
                    2. Махи на плечи с резиной 20 повторов
                    3. 20-25 отжиманий от пола
                    4. разрывы резины перед собой в динамике
                    5. отжимания от пола с колен алмазные в отказ
                    6. отдых 60-180 секунд
                    6. В моменте отдыха: 40 китайских приседаний и 40 скручиваний на пресс.
                    
                    3 подхода-раунда.
                """.trimIndent()
            }
            p {
                +"Тренировка (workoutId: ${workout.workoutId}):"
            }
            hr{}
            SetsHtml(call).render(this, workout.sets)
        }
    }
}

class SetsHtml(val call: ApplicationCall) {
    fun render(content: FlowContent, sets: List<SetResponse>) {
        content.div {
            id = "setsContainer"
            hr{}
            span { +"Сеты: ${sets.size}" }
            sets.forEach { set ->
                renderSet(this, set)
            }
        }
    }
    fun renderSet(content: FlowContent, set: SetResponse) {
        content.div {
            id = "set-${set.setId}"
            attributes["class"] = "set-item"
            span { +"Сет setId ${set.setId}:" }
            RoundsHtml(call).render(this, set.rounds)
        }
    }
}
class RoundsHtml(val call: ApplicationCall) {
    fun render(content: FlowContent, rounds: List<RoundResponse>) {
        content.div {
            id = "rounds-container"
            hr{}
            p { +"Раунды-подходы: ${rounds.size}"}
            rounds.forEach { round ->
                renderRound(this, round)
            }
        }
    }
    fun renderRound(content: FlowContent, round: RoundResponse) {
        content.div {
            id = "round-${round.roundId}"
            attributes["class"] = "round-item"
            span { +"раунд roundId: ${round.roundId}" }
            StepsHtml(call).render(this, round.steps)
        }
    }
}
class StepsHtml(val call: ApplicationCall) {
    fun render(content: FlowContent, steps: List<StepResponse>) {
        content.div {
            id = "steps-container"
            hr{}
            p { +"Шаги: ${steps.size}"}
            steps.forEach { step ->
                renderStep(this, step)
            }
        }
    }
    fun renderStep(content: FlowContent, step: StepResponse) {
        content.div {
            id = "step-${step.stepId}"
            attributes["class"] = "step-item"
            span { +"Шаг stepId: ${step.stepId}" }
            br{}
            span { +"упражнение exerciseName: ${step.exerciseName}" }
            br{}
            span { +"повторения reps: ${step.reps}" }
            br{}
            br{}
        }
    }
}