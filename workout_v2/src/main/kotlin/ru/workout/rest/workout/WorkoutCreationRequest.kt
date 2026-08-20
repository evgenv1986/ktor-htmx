package ru.workout.application.ru.workout.rest.workout

import kotlinx.serialization.Serializable
import ru.workout.application.ru.workout.usecase.workout.workoutCreateUseCaseDto

@Serializable
class WorkoutCreationRequest(
    val name: String
){
    var sets: List<SetStartingRequest> = listOf()
    fun toWorkoutDto(): workoutCreateUseCaseDto = workoutCreateUseCaseDto(
    )
}
@Serializable
class SetStartingRequest(val rounds: List<RoundStartingRequest>){}
@Serializable
class RoundStartingRequest(
    val id: String,
    val steps: List<StepStartingRequest>
)
@Serializable
class StepStartingRequest(val exercise: String, val reps: Int)

