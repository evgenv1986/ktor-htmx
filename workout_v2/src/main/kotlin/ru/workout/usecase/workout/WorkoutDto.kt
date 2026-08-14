package ru.workout.application.ru.workout.usecase.workout

import ru.workout.application.ru.workout.rest.workout.SetStartingRequest

open class WorkoutDto(

) {
    lateinit var sets: List<SetStartingRequest>
}