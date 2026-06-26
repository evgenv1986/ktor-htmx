package ru.workout.session.domain.additional

import ru.workout.common.types.base.ValueObject

data class Rep(
    val value: Int
): ValueObject {
    fun isReachedBy(other: Reps): Boolean {
        return value <= other.totalReps()
    }

    fun minus(completedReps: Reps): Rep {
        return Rep(value - completedReps.totalReps())
    }

    fun intValue(): Int {
        return value
    }
}

data class Reps(val repList: MutableList<Rep>){
    fun totalReps(): Int
        = repList.sumOf { it.value }

    fun add(rep: Rep) {
        repList.add(rep)
    }
    fun isNotEmpty(): Boolean = totalReps() > 0
    fun lastActualRep(): Rep = repList.last()
    fun isReachedBy(target: Rep): Boolean {
        return totalReps() >= target.intValue()
    }

}