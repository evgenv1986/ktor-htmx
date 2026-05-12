package ru.workout.rest.exercise

import workout.persistence.catalog.domain.workout.Exercise

open class ParsedExercise(val value: String) {
    fun name(): String {
        return value.replace(Regex("^\\d+\\.\\s*"), "")
//        return value.replace("1. ", "").trim()
    }

    fun toExercise(): Exercise {
        return Exercise(name())
    }
    override fun equals(other: Any?): Boolean{
        if (this === other) return true
        if (other !is ParsedExercise) return false
        return this.value == other.value
    }
    override fun hashCode(): Int {
        return value.hashCode()
    }
}