package ru.workout.application.ru.workout.rest.workout

import io.ktor.server.application.ApplicationCall
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
        CreateWorkoutEndpoint().handle(call)
    }
}

class CreateWorkoutEndpoint() {
    suspend fun handle(call: ApplicationCall) {
        val workoutId = call.parameters["workoutId"]!!
        val workoutRequest = call.receive<WorkoutCreationRequest>()
        call.respondHtml {
            body {
                div {
                    id = workoutId
                    p { workoutRequest.toString() }
                }}}
//        + TODO("распарсить workoutId из адресной строки")
//        + TODO("перенести парсинг workoutId в эндпоинт")
//        + TODO("извлечь workoutRequest данные тренировки с сетами, подходами итд")
//        TODO("создать метод workoutRequest.toWorkoutCreateUseCaseDto()")
//        TODO("создать workoutDto(внутри valueObjects) из workoutRequest, (workoutDto находится в модуле workout_v2.usecase.workout)")
//        TODO("создать create-Workout-Usecase, и передать его экземпляр в конструктор endpoint'a")
//        TODO("передать в агрументы endpoint'a экземпляр create-Workout-Usecase, вызвать usecase.invoke(workoutDto)")
//        TODO("проверить что usecase успешно создал агрегат тренировки")
    }
}