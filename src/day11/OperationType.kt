package day11

enum class OperationType {
    PLUS,
    TIMES,
}

fun Char.toOperationType() = when (this) {
    '+' -> OperationType.PLUS
    '*' -> OperationType.TIMES
    else -> throw IllegalArgumentException(this.toString())
}
