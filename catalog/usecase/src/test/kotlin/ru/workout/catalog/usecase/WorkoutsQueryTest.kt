package ru.workout.catalog.usecase

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import ru.workout.catalog.usecase.workout.WorkoutView
import workout.catalog.domain.TaskExercise
import workout.catalog.domain.Workout
import workout.catalog.domain.workoutWithStatusAdd
import workout.catalog.domain.WorkoutId

class WorkoutsQueryTest: StringSpec( {
    "should return workouts from findWorkout and converted to view"{
        val task = TaskExercise("name111")
        val tasks = listOf(task)
        val workout = workoutWithStatusAdd(tasks = tasks)
        val storage = LinkedHashMap<WorkoutId, Workout>()
        storage[workout.id] = workout
        val findWorkouts = FindWorkoutsImp(storage = storage)
        val workoutsViewQuery = WorkoutsQueryImp(findWorkouts)

        val result = workoutsViewQuery.invoke()

        result shouldHaveSize 1
        val findedWorkout = result[0]
        findedWorkout.id shouldBe workout.id
        findedWorkout.tasks[0].name shouldBe workout.tasks[0].name
    }
    "should successfully converted workout to workout View from FindWorkouts"{
        val task = TaskExercise("name")
        val tasks = listOf(task)
        val workoutsViewQuery = MockWorkoutsViewQuery(result = listOf(
            workoutView(tasks = tasks)))
        val result = workoutsViewQuery.invoke()
        result[0].tasks[0].name shouldBe "name"
    }
    "should convert workout to workoutView"{
        val workout = workoutWithStatusAdd()
        val view: WorkoutView = workout.toView()
        view.id shouldBe workout.id
        view.status shouldBe workout.status
        view.tasks shouldHaveSize workout.tasks.size
    }
    "should return domain workout from find workouts"{
        val task = TaskExercise("name")
        val tasks = listOf(task)
        val workout = workoutWithStatusAdd(tasks = tasks)
        val findWorkouts: FindWorkouts = MockFindWorkouts(result = listOf(workout))
        val result = findWorkouts.invoke()
        result shouldHaveSize tasks.size
        val workoutResult = result[0]
        workoutResult.tasks[0].name shouldBe task.name
    }
    "Workout query should return list workout view instance"{
        val query: WorkoutsViewQuery = MockWorkoutsViewQuery(
            listOf(
                workoutView(),
                workoutView())
        )
        val result = query.invoke()
        result.shouldBeInstanceOf<List<WorkoutView>>()
    }
    "should query return saved workouts"{
        val expected = listOf(
            workoutView(),
            workoutView()
        )
        val query: WorkoutsViewQuery = MockWorkoutsViewQuery(expected)
        val result = query.invoke()
        result shouldHaveSize (expected.size)
    }
})

open class FindWorkoutsImp(
    val storage: LinkedHashMap<WorkoutId, Workout>
) : FindWorkouts {
    override fun invoke(): List<Workout> {
        return storage.values.toList()
    }
}

open class WorkoutsQueryImp(val findWorkouts: FindWorkouts) : WorkoutsViewQuery {
    override fun invoke(): List<WorkoutView> {
        val workouts = findWorkouts.invoke()
        return workouts.map{ it.toView() }
    }

}

private fun Workout.toView() = WorkoutView (
    id,
    tasks,
    status
)

open class MockFindWorkouts(val result: List<Workout>) : FindWorkouts {
    override fun invoke(): List<Workout> {
        return result
    }

}

fun interface FindWorkouts {
    operator fun invoke(): List<Workout>
}

open class MockWorkoutsViewQuery(val result: List<WorkoutView>) : WorkoutsViewQuery {
    override fun invoke(): List<WorkoutView>{
        return result
    }
}

fun interface WorkoutsViewQuery {
    operator fun invoke(): List<WorkoutView>
}

