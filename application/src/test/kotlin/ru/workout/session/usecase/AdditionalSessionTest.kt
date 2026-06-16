package ru.workout.session.usecase

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import ru.workout.session.domain.SessionStatus
import ru.workout.session.usecase.additional.AdditionalSession
import ru.workout.session.usecase.additional.AdditionalTask
import ru.workout.session.usecase.additional.AdditionalTaskStatus

class AdditionalSessionTest: StringSpec({
    "can complete step"
//    "думаю сессию делать отдельно, в другом классе теста. могу посмотреть количество оставшегося времени выполнения упражнения - через сессию"
    {
        val handStand = taskHandstand(targetTime = 300)
        handStand.remainsCompleted() shouldBe 300
        handStand.status() shouldBe AdditionalTaskStatus.PLANNED

        val session = AdditionalSession(
            tasks = listOf(taskHandstand()),
            status = SessionStatus.IN_PROGRESS
        )
        session.completeStep(
            task = taskWithFirstStepCompleted(),
            step = step()
        )

    }
})