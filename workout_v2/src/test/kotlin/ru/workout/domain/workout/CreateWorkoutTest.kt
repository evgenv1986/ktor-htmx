package ru.workout.domain.workout

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import ru.workout.application.ru.workout.domain.workout.Round
import ru.workout.application.ru.workout.domain.workout.Set
import ru.workout.application.ru.workout.domain.workout.Step
import ru.workout.application.ru.workout.domain.workout.Workout
import ru.workout.application.ru.workout.domain.workout.WorkoutEvent
import ru.workout.application.ru.workout.domain.workout.WorkoutId
import ru.workout.application.ru.workout.domain.workout.WorkoutIdGenerator
import ru.workout.application.ru.workout.domain.workout.WorkoutStatus
import ru.workout.common.event.DomainEvent


class CreateWorkoutTest: StringSpec({
    "workout with task created successfully"{
        val workoutId = WorkoutId("w1")
        val idGenerator = MockWorkoutIdGenerator(workoutId)
        val workout = Workout.Companion.create(
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
        workout.status shouldBe WorkoutStatus.CREATED
    }
})

open class MockWorkoutIdGenerator(val workoutId: WorkoutId): WorkoutIdGenerator {
    override fun nextId(): WorkoutId = workoutId
}
