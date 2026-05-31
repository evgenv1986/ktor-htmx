package ru.workout.common.event


open interface DomainEventPublisher {
    fun publish(event: List<DomainEvent>) {

    }

}