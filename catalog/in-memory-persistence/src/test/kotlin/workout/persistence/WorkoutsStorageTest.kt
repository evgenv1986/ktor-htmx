package workout.persistence

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import workout.persistence.testFixtures.TestEventPublisher
import io.kotest.matchers.types.shouldBeSameInstanceAs
import workout.catalog.domain.WorkoutEvent
import workout.catalog.domain.workoutWithStatusAdd


class WorkoutsStorageTest: StringSpec ({
    "should save workout" {
        val publisher = TestEventPublisher()
        val repository = SaveWorkoutStorage(publisher)
        val workout = workoutWithStatusAdd()

        repository.save(workout)

        val storedWorkout = repository.storage[workout.id]
        storedWorkout shouldBeSameInstanceAs workout

        publisher.storage.shouldHaveSize(1)
        val event = publisher.storage.first()
        event.shouldBeInstanceOf<WorkoutEvent.Added>()
        event.workoutId shouldBe workout.id
    }
})

