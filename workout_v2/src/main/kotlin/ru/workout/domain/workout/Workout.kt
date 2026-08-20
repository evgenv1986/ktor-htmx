package ru.workout.application.ru.workout.domain.workout

import ru.workout.common.event.DomainEvent

class Workout(
    val sets: List<Set>,
    val id: WorkoutId,
    var status: WorkoutStatus,
) {
    private var events = ArrayList<DomainEvent>()
    companion object{
        fun create (
            sets: List<Set>,
            workoutIdGenerator: WorkoutIdGenerator
        ): Workout {
            val id = workoutIdGenerator.nextId()
            return Workout(
                sets,
                workoutIdGenerator.nextId(),
                WorkoutStatus.CREATED
            )
            .apply{
                addEvent(WorkoutEvent.StartedEvent(
                    id))
            }
        }
    }
    fun addEvent(event: DomainEvent) {
        events.add(event)
    }
    fun popEvents(): List<DomainEvent> {
        val res = events
        events = ArrayList()
        events.clear()
        return res
    }
}

class Set(val rounds: List<Round>)

class Round(val id: String, val steps: List<Step>)

class Step(val exercise: String, val reps: Int)

sealed class WorkoutEvent(val workoutId: WorkoutId): DomainEvent {
    class StartedEvent(workoutId: WorkoutId) : WorkoutEvent(workoutId)
}
interface WorkoutIdGenerator {
    fun nextId(): WorkoutId
}
data class WorkoutId(val value: String)
enum class WorkoutStatus {
    CREATED,
    PLANNED
}