package ru.workout.session.domain.additional

import ru.workout.common.types.base.ValueObject

open class Rep(
    val value: Int
): ValueObject {
    fun isReachedBy(other: List<Rep>): Boolean {
        return value <= other.sumOf { it.value }
    }
}

open class Reps(val repList: MutableList<Rep>){

}