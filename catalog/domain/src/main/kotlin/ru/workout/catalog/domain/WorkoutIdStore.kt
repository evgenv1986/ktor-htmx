package ru.workout.catalog.domain

interface WorkoutIdStore {
    fun generate(): WorkoutId
}