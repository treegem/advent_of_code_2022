package day11

import common.readInput

fun main() {

    fun part1(input: List<String>) = solve(input.toSortedMonkeys(), repetitions = 20) { it.div(3) }

    fun part2(input: List<String>): Long {
        val monkeys = input.toSortedMonkeys()

        return solve(monkeys, repetitions = 10_000) { newWorry ->
            val multipliedDivisors = monkeys.map { it.testDivisor }.reduce { acc, divisor -> acc * divisor }
            newWorry % multipliedDivisors
        }
    }

    val day = "11"

// test if implementation meets criteria from the description, like:
    val testInput = readInput("Day${day}_test")
    check(part1(testInput) == 10605L)
    check(part2(testInput) == 2_713_310_158L)

    val input = readInput("Day${day}")
    println(part1(input))
    println(part2(input))
}

private fun solve(monkeys: List<Monkey>, repetitions: Int, handleNewWorries: (Long) -> Long): Long {
    repeat(repetitions) {
        monkeys.forEach { monkey ->
            monkey.inspectReduceWorriesAndThrowItems(monkeys, handleNewWorries)
        }
    }

    return monkeys
        .map { it.inspectedItems }
        .sortedDescending()
        .take(2)
        .reduce { acc, inspectionCount -> acc * inspectionCount }
}



