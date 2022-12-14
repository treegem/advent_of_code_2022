package day13

sealed interface PacketContent {
    fun getOutcome(other: PacketContent): Int
}

class NumberContent(
    private val value: Int,
) : PacketContent {
    override fun getOutcome(other: PacketContent) =
        when (other) {
            is NumberContent -> getOutcome(other)
            is ListContent -> getOutcome(other)
        }

    private fun getOutcome(other: NumberContent) = this.value - other.value

    private fun getOutcome(other: ListContent) = ListContent(listOf(this)).getOutcome(other)

}

class ListContent(
    private val value: List<PacketContent>,
) : PacketContent {
    override fun getOutcome(other: PacketContent): Int {
        return when (other) {
            is NumberContent -> getOutcome(other)
            is ListContent -> getOutcome(other)
        }
    }

    private fun getOutcome(other: NumberContent) = other.getOutcome(this) * -1

    private fun getOutcome(other: ListContent): Int {
        this.value.zip(other.value).forEach {
            val outcome = it.first.getOutcome(it.second)
            if (outcome != 0) return outcome
        }
        return this.value.size - other.value.size
    }
}
