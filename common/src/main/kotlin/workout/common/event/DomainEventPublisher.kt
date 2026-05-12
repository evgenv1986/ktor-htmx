package workout.common.event

import workout.persistence.catalog.domain.workout.DomainEvent

open interface DomainEventPublisher {
    fun publish(event: List<DomainEvent>) {

    }

}