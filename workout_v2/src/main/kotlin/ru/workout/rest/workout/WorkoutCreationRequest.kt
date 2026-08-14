package ru.workout.application.ru.workout.rest.workout

import kotlinx.serialization.Serializable
import ru.workout.application.ru.workout.usecase.workout.WorkoutDto

@Serializable
class WorkoutCreationRequest(
    val sets: List<SetStartingRequest>
){
    fun toWorkoutDto(): WorkoutDto {
         TODO("not implemented")
    }
//    fun toWorkoutDto(): WorkoutDto =
//        val sets = listOf(WorkoutSet)-domain - set
//        WorkoutDto(
//            sets = sets
//        )
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

