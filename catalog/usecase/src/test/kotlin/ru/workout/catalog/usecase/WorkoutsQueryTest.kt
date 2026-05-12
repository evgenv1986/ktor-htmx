package ru.workout.catalog.usecase

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.types.shouldBeInstanceOf
import ru.workout.catalog.usecase.workout.WorkoutView

class WorkoutsQueryTest: StringSpec( {
    "Workout query should return list workout view instance"{
        val query: WorkoutsQuery = MockWorkoutsQuery(
            listOf(
                WorkoutView(),
                WorkoutView())
        )
        val result = query.invoke()
        result.shouldBeInstanceOf<List<WorkoutView>>()
    }
    "should query return saved workouts"{
        val expected = listOf(
            WorkoutView(),
            WorkoutView())
        val query: WorkoutsQuery = MockWorkoutsQuery(expected)
        val result = query.invoke()
        result shouldHaveSize (expected.size)
    }
})

open class MockWorkoutsQuery(val expected: List<WorkoutView>) : WorkoutsQuery {
    override fun invoke(): List<WorkoutView>{
        return expected
    }
}

fun interface WorkoutsQuery {
    operator fun invoke(): List<WorkoutView>
}

