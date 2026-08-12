package ru.workout.workout.domain.test

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import ru.workout.common.event.DomainEvent
import ru.workout.workout.domain.main.Round
import ru.workout.workout.domain.main.Set
import ru.workout.workout.domain.main.Step
import ru.workout.workout.domain.main.Workout
import ru.workout.workout.domain.main.WorkoutEvent
import ru.workout.workout.domain.main.WorkoutId
import ru.workout.workout.domain.main.WorkoutIdGenerator
import ru.workout.workout.domain.main.WorkoutStatus


class WorkoutTest: StringSpec({
    "workout with task started successfully"{
        val workoutId = WorkoutId("w1")
        val idGenerator = MockWorkoutIdGenerator(workoutId)
        val workout = Workout.start(
            sets = listOf(
                Set(
                    rounds = listOf(
                        Round(
                            id = "r1",
                            steps = listOf(
                                Step(exercise = "Подъем с переворотом", reps = 1),
                                Step(exercise = "Отжимания в упоре", reps = 10),
                                Step(exercise = "Подтягивания", reps = 5),
                            )
                        ),
                        Round(
                            id = "r2",
                            steps = listOf(
                                Step(exercise = "Подъем с переворотом", reps = 1),
                                Step(exercise = "Отжимания в упоре", reps = 10),
                                Step(exercise = "Подтягивания", reps = 5),
                            )
                        ),
                        Round(
                            id = "r3",
                            steps = listOf(
                                Step(exercise = "Подъем с переворотом", reps = 1),
                                Step(exercise = "Отжимания в упоре", reps = 10),
                                Step(exercise = "Подтягивания", reps = 5),
                            )
                        ),
                        Round(
                            id = "r4",
                            steps = listOf(
                                Step(exercise = "Подъем с переворотом", reps = 1),
                                Step(exercise = "Отжимания в упоре", reps = 10),
                                Step(exercise = "Подтягивания", reps = 5),
                            )
                        ),
                    )
                ),
                Set(
                    rounds = listOf(
                        Round(
                            id = "r1",
                            steps = listOf(
                                Step(exercise = "маятниковый австралийские подтягивания", reps = 15 - 20),
                                Step(exercise = "махи на плечи с резиной", reps = 30),
                            )
                        ),
                        Round(
                            id = "r1",
                            steps = listOf(
                                Step(exercise = "маятниковый австралийские подтягивания", reps = 15 - 20),
                                Step(exercise = "махи на плечи с резиной", reps = 30),
                            )
                        ),
                        Round(
                            id = "r1",
                            steps = listOf(
                                Step(exercise = "маятниковый австралийские подтягивания", reps = 15 - 20),
                                Step(exercise = "махи на плечи с резиной", reps = 30),
                            )
                        )
                    )
                )
            ),
            idGenerator
        )
        val events = workout.popEvents()
        val startedEvent: DomainEvent = events[0]
        startedEvent.shouldBeInstanceOf<WorkoutEvent.StartedEvent>()
        startedEvent.workoutId shouldBe workoutId
        workout.status shouldBe WorkoutStatus.STARTED
    }
})

open class MockWorkoutIdGenerator(val workoutId: WorkoutId): WorkoutIdGenerator {
    override fun nextId(): WorkoutId = workoutId
}
