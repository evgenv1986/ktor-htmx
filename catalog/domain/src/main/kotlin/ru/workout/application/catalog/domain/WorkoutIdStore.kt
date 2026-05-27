package ru.workout.application.catalog.domain

interface WorkoutIdStore {
    fun generate(): WorkoutId
}