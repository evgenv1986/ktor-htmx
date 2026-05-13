package workout.common.event

import workout.catalog.domain.DomainEvent

open interface DomainEventPublisher {
    fun publish(event: List<DomainEvent>) {

    }

}