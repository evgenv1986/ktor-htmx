package ru.workout.application.common.event


open interface DomainEventPublisher {
    fun publish(event: List<DomainEvent>) {

    }

}