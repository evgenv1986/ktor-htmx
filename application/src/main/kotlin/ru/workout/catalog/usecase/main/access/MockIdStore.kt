package ru.workout.catalog.usecase.access

import ru.workout.catalog.domain.WorkoutId
import ru.workout.catalog.domain.WorkoutIdStore
import java.util.UUID

open class MockIdStore: WorkoutIdStore {
    override fun generate(): WorkoutId {
        return WorkoutId(UUID.randomUUID())
    }
}