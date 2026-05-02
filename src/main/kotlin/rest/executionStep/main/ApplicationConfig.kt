package com.example.rest.executionStep.main

import com.example.rest.executionStep.main.input.InputStepEndpoint
import com.example.rest.executionStep.main.input.InputStepPerformView
import com.example.rest.executionStep.main.persist.StepCompleteEndpoint
import com.example.rest.executionStep.main.persist.StepCompleteRoute
import com.example.rest.executionStep.main.persist.SuccessStepCompleteViewResult
import com.example.rest.executionStep.main.step.InputStepRoute
import com.example.rest.executionStep.main.workout.WorkoutAddHandleEndPoint
import com.example.rest.executionStep.main.workout.WorkoutAddHandleRoute
import com.example.rest.executionStep.main.workout.WorkoutAddHandleView
import com.example.rest.executionStep.main.workout.WorkoutInputEndpoint
import com.example.rest.executionStep.main.workout.WorkoutInputRoute
import com.example.rest.executionStep.main.workout.WorkoutInputView
import io.ktor.server.application.*
import io.ktor.server.routing.*
import workout.catalog.usecase.AddWorkoutUseCase
import workout.catalog.usecase.InMemoryWorkoutAlreadyExits
import workout.catalog.usecase.InMemoryWorkoutStore
import workout.catalog.usecase.MockIdStore
import workout.catalog.usecase.MockSaveWorkout
import workout.catalog.usecase.WorkoutId
import workout.catalog.usecase.workout.Workout

class ApplicationConfig(val app: Application) {
    fun configureRoutes(){
        app.routing {
            InputStepRoute(
                this,
                InputStepEndpoint(
                    InputStepPerformView()
                ))
                .register()
            StepCompleteRoute(
                this,
                StepCompleteEndpoint(
                    SuccessStepCompleteViewResult()
                ))
                .register()
            WorkoutInputRoute(
                this,
                WorkoutInputEndpoint(
                    WorkoutInputView()
                )
            ).register()
            WorkoutAddHandleRoute(
                this,
                WorkoutAddHandleEndPoint(
                    AddWorkoutUseCase(
                        InMemoryWorkoutAlreadyExits(
                            InMemoryWorkoutStore(
                                mutableMapOf<WorkoutId, Workout>()
                            )
                        ),
                        MockSaveWorkout(),
                        MockIdStore()
                    ),
                    WorkoutAddHandleView()
                )
            ).register()
        }
    }
}