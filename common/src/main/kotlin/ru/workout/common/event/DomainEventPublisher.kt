package ru.workout.common.event

import ru.workout.common.event.DomainEvent

open interface DomainEventPublisher {
    fun publish(event: List<DomainEvent>) {

    }

}