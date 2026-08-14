package ru.workout.application.ru.workout.rest.workout

import io.ktor.server.application.call
import io.ktor.server.html.respondHtml
import io.ktor.server.request.receive
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import kotlinx.html.body
import kotlinx.html.div
import kotlinx.html.id
import kotlinx.html.p

fun Route.createWorkout(){
    post ("/workouts/{workoutId}/creation") {
        val workoutId = call.parameters["workoutId"]!!
        val workoutRequest = call.receive<WorkoutCreationRequest>()
        call.respondHtml {
            body {
                div {
                    id = workoutId
                    p { workoutRequest.toString() }
                }}}
    }
}

class CreateWorkoutEndpoint {
    fun handle(){
        """
            распарсить workoutId из адресной строки
            извлечь workoutRequest данные тренировки с сетами, подходами итд
            создать метод workoutRequest.toWorkoutCreateUseCaseDto()
            создать workoutDto(внутри valueObjects) из workoutRequest, (workoutDto находится в модуле workout_v2.usecase.workout)
            создать create-Workout-Usecase, и передать его экземпляр в конструктор endpoint'a
            передать в агрументы endpoint'a экземпляр create-Workout-Usecase, вызвать usecase.invoke(workoutDto)
            проверить что usecase успешно создал агрегат тренировки
        """.trimIndent()
    }
}