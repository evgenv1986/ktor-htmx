package ru.workout.session.domain.additional

import ru.workout.common.types.base.ValueObject

open class Rep(
    val value: Int
): ValueObject {
    fun isReachedBy(other: Reps): Boolean {
        return value <= other.totalReps()
    }
}

open class Reps(val repList: MutableList<Rep>){
    fun totalReps(): Int
        = repList.sumOf { it.value }

    fun add(rep: Rep) {
        repList.add(rep)
    }
    fun isNotEmpty(): Boolean = totalReps() > 0
}