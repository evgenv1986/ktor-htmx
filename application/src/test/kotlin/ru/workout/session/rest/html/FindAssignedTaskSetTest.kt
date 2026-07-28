package ru.workout.session.rest

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.testApplication
import ru.workout.application.module
import ru.workout.session.rest.html.PresentTaskResponse
import ru.workout.session.rest.html.TaskResponse
import ru.workout.session.usecase.task.TaskSetView

class FindAssignedTaskSetTest: StringSpec({
    "should return status Ok, on assigned-sets request"{
        testApplication {
            application { module() }
            val workoutId = 1
            val url = "/workouts/${workoutId}/sets/2/steps/3/task"
            val response = client.get(url)
            response.status shouldBe HttpStatusCode.OK
        }
    }
    "should return workout set step task html"{

    }
    "should return workout html text"{
        TODO("полный тескст тренировки, затем разбить на div контейнеры с подгрузкой get запросами")
    }
    "should create taskResponse from TaskSetView successfully"{
        val expectTaskSetResponse = TaskResponse(1, 2, 3, 4, "pull-ups")

        val setResponse: TaskResponse =
            PresentTaskResponse().toResponse(
                TaskSetView(1, 2, 3, 4, "pull-ups")
//                    .toView(TaskSet())
        )
//        data class PresentTaskSetResponse(val reps: Int)
//        data class TaskSetView(val reps: Reps)
//        data class TaskSet(val id: TaskSetId, val reps: Reps)

        setResponse shouldBe expectTaskSetResponse

//        // ниже закоментировать
//        testApplication {
//            application { module() }
//            val jsonClient = createClient {
//                install(ContentNegotiation) { json() }
//            }
//
//            val taskId = 123
//            val url = "/tasks/${taskId}/assigned-sets/1"
//            val response = client.get(url){
////                contentType(ContentType.Application.Json)
//            }
//            response.body<TaskResponse>() shouldBe expectTaskSetResponse
//        }
    }

    "should return status Ok, on completed-sets request"{
        testApplication {
            application { module() }
            val taskId = 123
            val response = client.get("/tasks/${taskId}/completed-sets/1")
            response.status shouldBe HttpStatusCode.Companion.OK
        }
    }
//    "find exercise set should return existed set"{
//        val taskId = 123
//        val setId = 1
//        val reps = 35
//        val note = "its a comment"
//        val set = ExerciseSet(taskId,
//            setId,
//            reps,
//            note)
//        val findSet = MockFindExerciseSet(set)
//        findSet.invoke() shouldBe set
//    }
//    "completed sets query should return setQueryDto"{
//        val taskId = 123
//        val setId = 1
//        val reps = 35
//        val note = "its a comment"
//        val set = ExerciseSet(taskId,
//            setId,
//            reps,
//            note)
//        val findSet = MockFindExerciseSet(set)
//        val query = SetByTaskQuery(findSet)
//        val exerciseSetView = query(taskId, setId)
//        val expect = ExerciseSetView(
//            taskId,
//            setId,
//            reps,
//            note
//        )
//        expect shouldBe exerciseSetView
//    }
//    "completed sets query should return error when set not found"{
//        val repository = MockExtractRepositorySet()
//        val query = SetCompletedQuery(repository)
//        val taskId = 999
//        val setId = 999
//        val result = query.execute(taskId, setId).shouldBeLeft()
//        result shouldBeLeft SetNotFoundError(taskId, setId)
//    }
})