package workout.persistence.catalog.domain.workout

class Exercise(val name: String) {
    fun name(): String {
        return name
    }
    override fun equals(other: Any?): Boolean{
        if (this === other) return true
        if (other !is Exercise) return false
        return this.name == other.name
    }
    override fun hashCode(): Int {
        return name.hashCode()
    }
}