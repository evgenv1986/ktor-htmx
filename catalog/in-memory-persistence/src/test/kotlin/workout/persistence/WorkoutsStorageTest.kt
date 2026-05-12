package workout.persistence

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import workout.persistence.testFixtures.TestEventPublisher
import workout.persistence.testFixtures.workoutWithStatusAdd
import io.kotest.matchers.types.shouldBeSameInstanceAs
import workout.persistence.catalog.domain.workout.WorkoutEvent

class WorkoutsStorageTest: StringSpec ({
    "should save workout" {
        val publisher = TestEventPublisher()
        val repository = WorkoutStorage(publisher)
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

