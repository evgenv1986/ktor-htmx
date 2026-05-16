package ru.workout.session

import java.util.UUID

data class WorkoutPlan(val workoutPlanId: UUID, val exercises: List<String>)