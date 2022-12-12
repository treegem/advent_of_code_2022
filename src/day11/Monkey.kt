package day11

import day11.OperationType.PLUS
import day11.OperationType.TIMES

data class Monkey(
    val identifier: Int,
    val itemWorryLevels: MutableList<Long>,
    val operationType: OperationType,
    val operand: String,
    val testDivisor: Int,
    val successTarget: Int,
    val failTarget: Int,
) {
    var inspectedItems = 0

    fun inspectReduceWorriesAndThrowItems(monkeys: List<Monkey>, worriesDivisor: Int) {
        val multipliedDivisors = monkeys.map { it.testDivisor }.reduce { acc, divisor -> acc * divisor }
        itemWorryLevels.forEach { itemWorryLevel ->
            val dynamicOperand = operand.toLongOrNull() ?: itemWorryLevel
            val newWorryLevel = when (operationType) {
                PLUS -> itemWorryLevel + dynamicOperand
                TIMES -> itemWorryLevel * dynamicOperand
            }.div(worriesDivisor)
            val itemReceiver = successTarget.takeIf { newWorryLevel % testDivisor == 0L } ?: failTarget
            monkeys[itemReceiver].itemWorryLevels.add(newWorryLevel % multipliedDivisors)
            inspectedItems += 1
        }
        itemWorryLevels.clear()
    }
}

fun List<String>.toSortedMonkeys() =
    this.filter { it.isNotBlank() }
        .chunked(6)
        .map { it.toMonkey() }
        .sortedBy { it.identifier }

private fun List<String>.toMonkey() =
    Monkey(
        identifier = this[0]
            .substringAfterLast(" ")
            .first()
            .digitToInt(),
        itemWorryLevels = this[1]
            .substringAfterLast(": ")
            .split(", ")
            .map { it.toLong() }
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
