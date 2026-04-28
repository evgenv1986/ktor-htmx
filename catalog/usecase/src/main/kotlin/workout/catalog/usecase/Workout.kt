package workout.catalog.usecase.workout

import workout.catalog.usecase.WorkoutStatus

open class Workout(val status: WorkoutStatus = WorkoutStatus.DRAFT) {
    fun popEvents(): List<String> {
        TODO("Not yet implemented")
    }

}