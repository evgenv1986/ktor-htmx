package ru.workout.catalog.rest

import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.html.respondHtml
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import kotlinx.html.ButtonType
import kotlinx.html.FlowContent
import kotlinx.html.body
import kotlinx.html.br
import kotlinx.html.button
import kotlinx.html.div
import kotlinx.html.form
import kotlinx.html.h3
import kotlinx.html.id
import kotlinx.html.textArea
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import ru.workout.rest.WORKOUT_PLANS
import ru.workout.rest.WORKOUT_PLANS_NEW
import ru.workout.session.domain.WorkoutPlan

class PlanWorkoutViewRoute(
    private val routing: Routing,
    private val planWorkoutViewEndpoint: PlanWorkoutViewEndpoint
) {
    fun register() {
        routing.get(WORKOUT_PLANS_NEW) {
            planWorkoutViewEndpoint.handle(call)
        }
    }
}
class PlanWorkoutViewEndpoint(
    val planWorkoutView: PlanWorkoutView) {
    suspend fun handle(call: ApplicationCall) {
        val planWorkout = WorkoutPlan()
        call.respondHtml {
            body {
                with(planWorkoutView) {
                    invoke(planWorkout)
                }
            }
        }
    }
}
class PlanWorkoutView {
    fun FlowContent.invoke(workoutInput: WorkoutPlan){
        return div {
            id = "performance-input-container"
            form {
                attributes["hx-ext"] = "json-enc"
                attributes["hx-post"] = WORKOUT_PLANS //"/workouts/plannings/add"
                attributes["hx-target"] = "#performance-input-container"
                attributes["hx-swap"] = "outerHTML"

                h3 {+"""
                        Введите текст тренировки
                    """.trimIndent()
                }
                br {}
                textArea {
                    name = "workoutText"
                    rows = "5"
                    cols = "80"
                    placeholder = "Введите задание тренировки"
//                    required = true
                    id = "workout-text-input"
                }
                button {
                    type = ButtonType.submit
                    +"Сохранить тренировку"
                }
            }
        }
    }
}

//object UuidSerializer : KSerializer<UUID> {
//    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("UUID", PrimitiveKind.STRING)
//
//    override fun serialize(encoder: Encoder, value: UUID) {
//        encoder.encodeString(value.toString())
//    }
//
//    override fun deserialize(decoder: Decoder): UUID {
//        return UUID.fromString(decoder.decodeString())
//    }
//}

//@Serializable
//data class WorkoutPlan(
//    @Serializable(with = UuidSerializer::class)
//    var catalogWorkoutId: UUID = UUID.randomUUID(),
//    var workoutText: String = ""
//)