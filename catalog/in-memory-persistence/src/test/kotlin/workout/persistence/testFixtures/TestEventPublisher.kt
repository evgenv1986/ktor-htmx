package workout.persistence.testFixtures

import workout.common.event.DomainEventPublisher
import workout.persistence.catalog.domain.workout.DomainEvent

open class TestEventPublisher: DomainEventPublisher {
    val storage = ArrayList<DomainEvent>()
    override fun publish(events: List<DomainEvent>) {
        storage.addAll(events)
    }
}