package ru.workout.application.training.domain

import java.util.UUID

data class WorkoutPlan(val workoutPlanId: UUID, val exercises: List<String>)