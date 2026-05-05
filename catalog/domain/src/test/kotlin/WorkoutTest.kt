import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class WorkoutTest: StringSpec({
    "should parse workout text with two exercises"{
        val input =  """
            1. Подтягивания
            2. Отжимания
        """.trimIndent()
        val exercises = ParsedWorkout().parse(input)
        exercises.size shouldBe 2
        exercises[0].name() shouldBe "Подтягивания"
        exercises[1].name() shouldBe "Отжимания"
    }
})

open class ParsedWorkout {
    fun parse(text: String): List<Exercise>{
        return text.lines()
            .filter { it.isNotBlank() }
            .map { line -> ParsedExercise(line).toExercise() }
    }

}
