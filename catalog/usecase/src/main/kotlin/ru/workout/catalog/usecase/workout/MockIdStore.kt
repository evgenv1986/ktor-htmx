package ru.workout.catalog.usecase.workout

import workout.catalog.domain.WorkoutId
import workout.catalog.domain.WorkoutIdStore

open class MockIdStore: WorkoutIdStore {
    override fun generate(): WorkoutId {
        return WorkoutId(1)
    }
}

