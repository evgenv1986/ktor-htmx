import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

class ExerciseTest: StringSpec({
    "should extract name from simple string"{
        ParsedExercise(
            """
                1. Подтягивания
            """.trimIndent()
        ).name().equals( ParsedExercise("Подтягивания"))
    }
    "should create exercise from parsed exercise"{
        ParsedExercise(
            """
                1. Подтягивания
            """.trimIndent()
        ).toExercise().shouldBeInstanceOf<Exercise>()
        ParsedExercise(
            """
                1. Подтягивания
            """.trimIndent()
        ).toExercise().name() shouldBe "Подтягивания"
        ParsedExercise(
            """
                1. Подтягивания
            """.trimIndent()
        ).toExercise().equals(Exercise("Подтягивания"))
    }
})

class Exercise(val name: String) {
    fun name(): String {
        return name
    }

}

open class ParsedExercise(val value: String) {
    fun name(): String {
        return value.replace(Regex("^\\d+\\.\\s*"), "")
//        return value.replace("1. ", "").trim()
    }

    fun toExercise(): Exercise {
        return Exercise(name())
    }
}
