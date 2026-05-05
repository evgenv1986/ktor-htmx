import io.kotest.core.spec.style.StringSpec

class ExerciseTest: StringSpec({
    "should extract name from simple string"{
        ParsedExercise(
            """
                1. Подтягивания
            """.trimIndent()
        ).name().equals( ParsedExercise("Подтягивания"))
    }
})

open class ParsedExercise(val value: String) {
    fun name(): String {
        return value.replace("1. ", "").trim()
    }
}
