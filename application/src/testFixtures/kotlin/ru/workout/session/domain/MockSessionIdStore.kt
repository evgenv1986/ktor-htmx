package ru.workout.session.domain

open class MockSessionIdStore: SessionIdStore {
    override fun nextId(): Int = 123
}