package ru.workout.session.domain

import ru.workout.common.event.DomainEvent

open class DomainEntity {
    protected var events = ArrayList<DomainEvent>()
    open fun addEvent(event: DomainEvent){
        events.add(event)
    }
    open fun popEvents(): List<DomainEvent> {
        val result = events
        events = ArrayList()
        events.clear()
        return result
    }
}
