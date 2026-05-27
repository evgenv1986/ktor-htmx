package ru.workout.application.catalog.domain

interface WorkoutAlreadyExist {
    operator fun invoke(workoutText: List<TaskExercise>): Boolean
}
