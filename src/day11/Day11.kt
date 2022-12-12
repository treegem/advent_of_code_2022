package day11

import readInput

fun main() {

    fun part1(input: List<String>) = solve(input, repetitions = 20, worriesDivisor = 3)

    fun part2(input: List<String>) = solve(input, repetitions = 10_000, worriesDivisor = 1)

    val day = "11"

// test if implementation meets criteria from the description, like:
    val testInput = readInput("Day${day}_test")
    check(part1(testInput) == 10605L)
    check(part2(testInput) == 2_713_310_158L)

    val input = readInput("Day${day}")
    println(part1(input))
    println(part2(input))
}

private fun solve(input: List<String>, repetitions: Int, worriesDivisor: Int): Long {
    val monkeys = input.toSortedMonkeys()
    repeat(repetitions) {
        monkeys.forEach { monkey ->
            monkey.inspectReduceWorriesAndThrowItems(monkeys, worriesDivisor)
        }
    }

    return monkeys
        .map { it.inspectedItems.toLong() }
        .sortedDescending()
        .take(2)
        .reduce { acc, inspectionCount -> acc * inspectionCount }
}



