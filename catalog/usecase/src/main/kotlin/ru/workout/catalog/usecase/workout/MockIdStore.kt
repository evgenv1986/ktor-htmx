package ru.workout.catalog.usecase.workout

import workout.catalog.domain.workout.WorkoutId
import workout.catalog.domain.workout.WorkoutIdStore

open class MockIdStore: WorkoutIdStore {
    override fun generate(): WorkoutId {
        return WorkoutId(1)
    }
}

