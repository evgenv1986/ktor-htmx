package ru.workout.session.usecase

import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import ru.workout.catalog.usecase.MockExtractSessionStore
import ru.workout.provider.workoutPlan.MockWorkoutPlanProvider
import ru.workout.session.domain.MockSessionIdStore
import ru.workout.session.domain.SessionStatus
import ru.workout.session.domain.WorkoutPlan
import ru.workout.session.usecase.prepareSession.PrepareSessionRequest
import ru.workout.session.usecase.prepareSession.PrepareSessionUseCase

//class PrepareSessionUseCaseTest: StringSpec({
//    "UseCase creates session from plan"{
//        val planId = 123
//        val expectedPlan = WorkoutPlan(planId, listOf("Приседания", "Подтягивания"))
//        val provider = MockWorkoutPlanProvider(expectedPlan)
//
//        val extractSessionStore = MockExtractSessionStore()
//        val saveSessionStore = MockSaveWorkoutSession()
//        val idStore = MockSessionIdStore()
//        val prepareSessionRequest = PrepareSessionRequest(planId)
//
//        val useCase = PrepareSessionUseCase(
//            provider = provider,
//            extractSession = extractSessionStore,
//            saveSessionStore = saveSessionStore,
//            idStore = idStore
//        )
//
//        val session = useCase(prepareSessionRequest)
//
//        val result = session.shouldBeRight()
//        val workoutSession = extractSessionStore.session !!
//        workoutSession.status shouldBe SessionStatus.PREPARED
//        workoutSession.planId shouldBe planId
//        workoutSession.exercises shouldBe listOf("Приседания","Подтягивания")
//    }
//})