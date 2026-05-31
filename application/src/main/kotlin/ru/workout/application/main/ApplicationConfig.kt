package ru.workout.application

import io.ktor.server.application.*
import io.ktor.server.routing.*
import ru.workout.catalog.domain.Workout
import ru.workout.catalog.domain.WorkoutId
import ru.workout.catalog.in_memoty_persistence.WorkoutAlreadyExitsInMemory
import ru.workout.catalog.in_memoty_persistence.FindWorkoutByExercisesImp
import ru.workout.catalog.usecase.access.MockIdStore
import ru.workout.catalog.in_memory_persistence.main.SaveWorkoutStorage
import ru.workout.catalog.rest.SubmitWorkoutPlanEndPoint
import ru.workout.catalog.rest.SubmitWorkoutPlanRoute
import ru.workout.catalog.rest.WorkoutPlanView
import ru.workout.catalog.rest.PlanWorkoutViewEndpoint
import ru.workout.catalog.rest.PlanWorkoutViewRoute
import ru.workout.catalog.rest.PlanWorkoutView
import ru.workout.session.usecase.PlanWorkoutUseCase
import workout.application.event.DomainEventPublisherImp

//import ru.workout.catalog.domain.Workout
//import ru.workout.catalog.domain.WorkoutId
//import ru.workout.catalog.usecase.workout.AddWorkoutUseCase
//import ru.workout.catalog.usecase.workout.InMemoryWorkoutAlreadyExits
//import ru.workout.catalog.usecase.workout.InMemoryWorkoutStore
//import ru.workout.catalog.usecase.workout.MockIdStore
//
//import ru.workout.rest.step.InputStepRoute
//import ru.workout.rest.step.input.InputStepEndpoint
//import ru.workout.rest.step.input.InputStepPerformView
//import ru.workout.rest.step.persist.StepCompleteEndpoint
//import ru.workout.rest.step.persist.StepCompleteRoute
//import ru.workout.rest.step.persist.SuccessStepCompleteViewResult
//import ru.workout.rest.workout.WorkoutAddHandleEndPoint
//import ru.workout.rest.workout.WorkoutAddHandleRoute
//import ru.workout.rest.workout.WorkoutAddHandleView
//import ru.workout.rest.workout.WorkoutInputEndpoint
//import ru.workout.rest.workout.WorkoutInputRoute
//import ru.workout.rest.workout.WorkoutInputView
//
//import workout.application.event.DomainEventPublisherImp
////import ru.workout.persistence.SaveWorkoutStorage
//
class ApplicationConfig(val app: Application) {
    fun     configureRoutes(){
        app.routing {
//                InputStepRoute(
//                this,
//                InputStepEndpoint(
//                    InputStepPerformView()
//                )
//            )
//                .register()
//            StepCompleteRoute(
//                this,
//                StepCompleteEndpoint(
//                    SuccessStepCompleteViewResult()
//                )
//            ).register()
            PlanWorkoutViewRoute(
                this,
                PlanWorkoutViewEndpoint(
                    PlanWorkoutView()
                )
            ).register()
            SubmitWorkoutPlanRoute(
                this,
                SubmitWorkoutPlanEndPoint(
                    PlanWorkoutUseCase(
                        WorkoutAlreadyExitsInMemory(
                            FindWorkoutByExercisesImp(mutableMapOf<WorkoutId, Workout>())
                        ),
                        SaveWorkoutStorage(DomainEventPublisherImp()),
                        MockIdStore()
                    ),
                    WorkoutPlanView()
                )
            ).register()
        }
    }
}