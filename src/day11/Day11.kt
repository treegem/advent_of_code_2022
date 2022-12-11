package day11

import readInput

fun main() {

    fun part1(input: List<String>): Int {
        val monkeys = input.toMonkeys()
        repeat(20) {
            monkeys.forEach { monkey ->
                monkey.inspectAndThrowItems(monkeys)
            }
        }
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val day = "11"

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day${day}_test")
    check(part1(testInput) == 10605)

    val input = readInput("Day${day}")
    println(part1(input))
    println(part2(input))
}



