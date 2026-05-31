package ru.workout.catalog.domain

interface WorkoutAlreadyExist {
    operator fun invoke(workoutText: List<TaskExercise>): Boolean
}