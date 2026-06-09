package ru.workout.session.domain

import ru.workout.catalog.domain.TaskExercise

interface SessionIdStore {
    fun nextId(): Int
}