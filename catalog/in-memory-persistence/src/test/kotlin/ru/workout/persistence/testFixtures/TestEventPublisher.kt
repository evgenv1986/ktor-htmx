//package ru.workout.persistence.testFixtures
//
//import ru.workout.common.event.DomainEventPublisher
//import ru.workout.common.event.DomainEvent
//
//open class TestEventPublisher: DomainEventPublisher {
//    val storage = ArrayList<DomainEvent>()
//    override fun publish(events: List<DomainEvent>) {
//        storage.addAll(events)
//    }
//}