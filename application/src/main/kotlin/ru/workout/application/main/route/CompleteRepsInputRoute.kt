package ru.workout.application.main.route

import io.ktor.server.application.call
import io.ktor.server.html.respondHtml
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import kotlinx.html.ButtonType
import kotlinx.html.body
import kotlinx.html.br
import kotlinx.html.button
import kotlinx.html.div
import kotlinx.html.form
import kotlinx.html.h3
import kotlinx.html.id
import kotlinx.html.label
import kotlinx.html.radioInput
import kotlinx.html.style
import kotlinx.html.textArea
import kotlinx.html.unsafe
import ru.workout.rest.COMPLETION_REP
import ru.workout.rest.COMPLETION_REPS_NEW
import javax.management.Query.div

fun Routing.completeInputRepsRoute() {
    CompleteInputRepsRoute(
        routing = this,
//        CompleteStepEndPoint(
//            MockCompleteStepUseCase()
//        )
    ).register()
}
class CompleteInputRepsRoute(
    private val routing: Routing,
//    private val completeStepEndPoint: CompleteStepEndPoint
) {
    fun register() {
        routing.get(COMPLETION_REPS_NEW) {
            call.respondHtml {
                body {
                    div {

                        id = "completion-input-container"
                        form {
                            attributes["hx-ext"] = "json-enc"
                            attributes["hx-post"] = COMPLETION_REP
                            attributes["hx-target"] = "#completion-input-container"
                            attributes["hx-swap"] = "outerHTML"

                            h3 {
                                +"""
                                Введите название упражнения и количество выполненных повторений
                                """.trimIndent()
                            }
                            br {}
                            textArea {
                                name = "exerciseName"
                                rows = "2"
                                cols = "80"
                                placeholder = "Введите название упражнения"
                                id = "exercise-name-input"
                            }
                            br {}
                            label("reps-option") {
                                radioInput {
                                    name = "reps"       // одинаковое имя у всех — это одна группа
                                    value = "0"
                                }
                                +" 0 повторений"
                            }

                            label("reps-option") {
                                radioInput {
                                    name = "reps"       // одинаковое имя у всех — это одна группа
                                    value = "5"
                                }
                                +" 5 повторений"
                            }

                            label("reps-option") {
                                radioInput {
                                    name = "reps"
                                    value = "10"
                                }
                                +" 10 повторений"
                            }

                            label("reps-option") {
                                radioInput {
                                    name = "reps"
                                    value = "15"
                                    checked = true      // выбран по умолчанию
                                }
                                +" 15 повторений"
                            }
                            button {
                                type = ButtonType.submit
                                +"Сохранить выполненные повторения"
                            }
                        }

                        style {
                            unsafe {
                                +"""
                                /* прячем стандартный кружок */
                                .reps-option input {
                                    display: none;
                                }
                        
                                /* label выглядит как кнопка */
                                .reps-option {
                                    display: inline-block;
                                    padding: 14px 22px;
                                    margin: 4px;
                                    border: 2px solid #d1d5db;
                                    border-radius: 10px;
                                    background: #f9fafb;
                                    color: #374151;
                                    font-size: 16px;
                                    font-weight: bold;
                                    cursor: pointer;
                                    user-select: none;
                                    transition: all 0.15s ease;
                                }
                        
                                /* при наведении */
                                .reps-option:hover {
                                    border-color: #93c5fd;
                                }
                        
                                /* эффект нажатия */
                                .reps-option:active {
                                    transform: scale(0.96);
                                }
                        
                                /* ВЫБРАННЫЙ вариант — сам label подсвечивается */
                                .reps-option:has(input:checked) {
                                    background: #3b82f6;
                                    border-color: #2563eb;
                                    color: white;
                                }
                                """.trimIndent()
                            }
                        }
                    }
                }
            }
        }
    }
}

