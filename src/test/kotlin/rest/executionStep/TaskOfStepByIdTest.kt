package rest.executionStep

import com.example.rest.executionStep.main.task.TaskOfStepById
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class TaskOfStepByIdTest: StringSpec({
    "should returned task by id"{
        val task = TaskOfStepById(id = 123)
        task.invoke().exerciseName shouldBe "Подтягивания"
        task.invoke().weight shouldBe 12.1
        task.invoke().reps shouldBe 20
    }
})