package day11

import day11.OperationType.PLUS
import day11.OperationType.TIMES

data class Monkey(
    val identifier: Int,
    val itemWorryLevels: MutableList<Int>,
    val operationType: OperationType,
    val operand: String,
    val testDivisor: Int,
    val successTarget: Int,
    val failTarget: Int,
) {
    fun inspectAndThrowItems(monkeys: List<Monkey>) {
        itemWorryLevels.forEach {
            val dynamicOperand = operand.toIntOrNull() ?: it
            val newWorryLevel = when (operationType) {
                PLUS -> it + dynamicOperand
                TIMES -> it * dynamicOperand
            }.div(3)
        }
    }
}

fun List<String>.toMonkeys() =
    this.filter { it.isNotBlank() }
        .chunked(6)
        .map { it.toMonkey() }

private fun List<String>.toMonkey() =
    Monkey(
        identifier = this[0]
            .substringAfterLast(" ")
            .first()
            .digitToInt(),
        itemWorryLevels = this[1]
            .substringAfterLast(": ")
            .split(", ")
            .map { it.toInt() }
            .toMutableList(),
        operationType = this[2]
            .substringAfter("old ")
            .first()
            .toOperationType(),
        operand = this[2]
            .substringAfterLast(" "),
        testDivisor = this[3]
            .substringAfterLast(" ")
            .toInt(),
        successTarget = this[4]
            .substringAfterLast(" ")
            .toInt(),
        failTarget = this[5]
            .substringAfterLast(" ")
            .toInt()
    )
