package ru.workout.catalog.domain

class TaskExercise(val name: String) {
    fun name(): String {
        return name
    }
    override fun equals(other: Any?): Boolean{
        if (this === other) return true
        if (other !is TaskExercise) return false
        return this.name == other.name
    }
    override fun hashCode(): Int {
        return name.hashCode()
    }
}