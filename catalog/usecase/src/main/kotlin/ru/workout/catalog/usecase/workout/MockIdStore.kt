package ru.workout.catalog.usecase.workout

import workout.persistence.catalog.domain.workout.WorkoutId
import workout.persistence.catalog.domain.workout.WorkoutIdStore

open class MockIdStore: WorkoutIdStore {
    override fun generate(): WorkoutId {
        return WorkoutId(1)
    }
}

