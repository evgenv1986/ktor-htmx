package ru.workout.common.event


interface DomainEventPublisher {
    fun publish(event: List<DomainEvent>) {

    }

}