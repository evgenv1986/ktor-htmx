package ru.workout.provider.workoutPlan

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import ru.workout.catalog.domain.WorkoutId
import ru.workout.catalog.in_memory_persistence.ExtractWorkoutStorageById
import ru.workout.catalog.in_memoty_persistence.WorkoutStoreError
import ru.workout.session.domain.WorkoutPlan
import ru.workout.session.usecase.WorkoutPlanProvider
import ru.workout.session.usecase.WorkoutProviderError
import java.util.UUID

class CatalogWorkoutPlanProvider(
    val store: ExtractWorkoutStorageById
) : WorkoutPlanProvider {
    override fun workoutPlanById(workoutPlanId: Int)
    : Either<WorkoutProviderError, WorkoutPlan> = either {
        val workoutId = WorkoutId(workoutPlanId)
        val catalogWorkout = store.invoke(workoutId)
            .mapLeft { WorkoutProviderError.WorkoutNotFound }
            .bind()
        WorkoutPlan.from(catalogWorkout)
    }
}

