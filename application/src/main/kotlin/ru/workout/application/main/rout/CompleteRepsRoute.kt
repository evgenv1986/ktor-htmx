package ru.workout.application.main.rout

import io.ktor.server.application.call
import io.ktor.server.html.respondHtml
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import kotlinx.html.ButtonType
import kotlinx.html.InputType
import kotlinx.html.body
import kotlinx.html.br
import kotlinx.html.button
import kotlinx.html.div
import kotlinx.html.form
import kotlinx.html.h3
import kotlinx.html.id
import kotlinx.html.input
import kotlinx.html.label
import kotlinx.html.option
import kotlinx.html.radioInput
import kotlinx.html.select
import kotlinx.html.style
import kotlinx.html.textArea
import kotlinx.html.unsafe
import ru.workout.rest.COMPLETE_STEP
import ru.workout.rest.COMPLETION_REPS_NEW
import ru.workout.rest.WORKOUT_PLANS

fun Routing.completeRepsRoute() {
    CompleteRepsRoute(
        routing = this,
//        CompleteStepEndPoint(
//            MockCompleteStepUseCase()
//        )
    ).register()
}
class CompleteRepsRoute(
    private val routing: Routing,
//    private val completeStepEndPoint: CompleteStepEndPoint
) {
    fun register() {
        routing.get(COMPLETION_REPS_NEW) {
            call.respondHtml {
                body {
                    id = "performance-input-container"
                    form {
                        attributes["hx-ext"] = "json-enc"
                        attributes["hx-post"] = WORKOUT_PLANS //"/workouts/plannings/add"
                        attributes["hx-target"] = "#performance-input-container"
                        attributes["hx-swap"] = "outerHTML"

                        h3 {+"""
                            Введите количество выполненных повторений
                            """.trimIndent()
                        }
                        br {}

                        label("reps-option")  {
                            radioInput {
                                name = "reps"       // одинаковое имя у всех — это одна группа
                                value = "5"
                            }
                            +" 5 повторений"
                        }

                        label("reps-option")  {
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

                        label("reps-option")  {
                            radioInput {
                                name = "reps"
                                value = "15"
                                checked = true      // выбран по умолчанию
                            }
                            +" 15 повторений"
                        }

                    }

                        button {
                            type = ButtonType.submit
                            +"Сохранить тренировку"
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
//            completeStepEndPoint.handle(call)
        }
    }
}