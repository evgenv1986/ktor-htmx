package ru.workout.session.rest.set

import io.ktor.http.HttpStatusCode
import kotlinx.html.FlowContent
import io.ktor.server.application.ApplicationCall
import io.ktor.server.html.respondHtml
import kotlinx.html.ButtonType
import kotlinx.html.InputType
import kotlinx.html.body
import kotlinx.html.button
import kotlinx.html.div
import kotlinx.html.form
import kotlinx.html.h2
import kotlinx.html.h3
import kotlinx.html.id
import kotlinx.html.input
import kotlinx.html.li
import kotlinx.html.p
import kotlinx.html.ul
import kotlin.collections.isNotEmpty

class EntrySetForm(val call: ApplicationCall) {
    suspend fun view(taskId: String?) {
        call.respondHtml(HttpStatusCode.OK) {
            body {
                render(taskId)
            }
        }
    }
    fun FlowContent.render(taskId: String?) {
        val task_exerciseName = "Подтягивания"
        val task_targetSets = 3
        val task_targetReps = 300
        val completedSets = 1
        val task_completedReps = 30
        val sets: MutableList<Int> = mutableListOf()
        sets.add(10)
        sets.add(20)
        sets.add(30)
        div {
            h2 { +"Упражнение: ${task_exerciseName}" }
            p { +"Цель: ${task_targetReps} повторений" }
            p {
                id = "progress-info"
                +"Выполнено: ${task_completedReps}, "
                +"осталось: ${task_targetReps - task_completedReps}"
            }

            form {
                attributes["hx-post"] = "/tasks/${taskId}/sets"
                attributes["hx-target"] = "#progress-info"
                attributes["hx-swap"] = "outerHTML"

                input(type = InputType.number, name = "reps") {
                    placeholder = "Количество повторений"
                    required = true
                    min = "1"
                    width = "1"
                }
                button(type = ButtonType.submit) { +"Выполнил" }
            }

            if (sets.isNotEmpty()) {
                h3 { +"Выполненные подходы:" }
                ul {
                    sets.forEach { set ->
                        li { +"Подход: ${set} повторений" }
                    }
                }
            }

        }

    }
}