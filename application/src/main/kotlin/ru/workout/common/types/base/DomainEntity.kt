package ru.workout.common.types.base

import ru.workout.common.event.DomainEvent

open class DomainEntity {
    private var events = ArrayList<DomainEvent>()
    fun popEvents(): List<DomainEvent> {
        val res = events
        events = ArrayList()
        events.clear()
        return res
    }
}