package ru.workout.training.domain

import java.util.UUID

data class WorkoutPlan(val workoutPlanId: UUID, val exercises: List<String>)