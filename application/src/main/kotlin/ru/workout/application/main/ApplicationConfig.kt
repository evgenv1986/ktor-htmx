package ru.workout.application

import io.ktor.server.application.*
import io.ktor.server.routing.*
import ru.workout.application.main.route.registerAllRoutes
import ru.workout.catalog.domain.Workout
import ru.workout.catalog.domain.WorkoutId
import ru.workout.catalog.in_memoty_persistence.WorkoutAlreadyExitsInMemory
import ru.workout.catalog.in_memoty_persistence.FindWorkoutByExercisesImp
import ru.workout.catalog.usecase.access.MockIdStore
import ru.workout.catalog.in_memory_persistence.main.SaveWorkoutStorage
import ru.workout.catalog.rest.BeginWorkoutEndPoint
import ru.workout.catalog.rest.BeginWorkoutRoute
import ru.workout.catalog.rest.BeginWorkoutUseCase
import ru.workout.catalog.rest.SubmitWorkoutPlanEndPoint
import ru.workout.catalog.rest.SubmitWorkoutPlanRoute
import ru.workout.catalog.rest.WorkoutPlanView
import ru.workout.catalog.rest.PlanWorkoutViewEndpoint
import ru.workout.catalog.rest.PlanWorkoutViewRoute
import ru.workout.catalog.rest.PlanWorkoutView
import ru.workout.catalog.usecase.main.PlanWorkoutUseCase
import workout.application.event.DomainEventPublisherImp


class ApplicationConfig(val app: Application) {
    fun configureRoutes(){
        app.routing {
            registerAllRoutes()
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

            BeginWorkoutRoute(
                routing = this,
                BeginWorkoutEndPoint(
                    BeginWorkoutUseCase()
                )
            ).register()

//            CompleteStepRoute(
//                routing = this,
//                CompleteStepEndPoint(
//                    CompleteStepUseCase()
//                )
//            ).register()
        }
    }
}